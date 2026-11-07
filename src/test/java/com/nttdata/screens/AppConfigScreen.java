package com.nttdata.screens;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.OutputType;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * AppConfigScreen
 * ---------------
 * UNICA responsabilidad de esta clase: levantar y cerrar la aplicacion
 * (es decir, crear y finalizar la sesion de Appium).
 * <p>
 * Esta clase NO conoce ningun elemento de UI (botones, campos, etc).
 * Eso es responsabilidad de cada Screen especifica de cada app.
 * <p>
 * Es GENERICA: sirve para levantar cualquier aplicacion Android.
 */
public class AppConfigScreen {

    private static AndroidDriver driver;

    public void iniciarAplicacion(String archivoConfiguracion) {

        if (driver != null) {
            System.out.println("[AppConfigScreen] Ya existe una sesion Appium activa, se reutiliza.");
            return;
        }

        System.out.println("[AppConfigScreen] Leyendo configuracion desde: " + archivoConfiguracion);
        Properties properties = cargarPropiedades(archivoConfiguracion);

        try {
            System.out.println("[AppConfigScreen] Construyendo capabilities...");
            UiAutomator2Options options = construirOptions(properties);
            String appiumUrl = obtenerObligatorio(properties, "appium.url");

            System.out.println("[AppConfigScreen] Conectando a Appium Server en: " + appiumUrl);
            driver = new AndroidDriver(new URL(appiumUrl), options);

            // Solución directa: Lanzar la actividad principal mediante ADB shell para evitar restricciones de Android 15
            String appPackage = properties.getProperty("appPackage");
            String appActivity = properties.getProperty("appActivity");

            System.out.println("[AppConfigScreen] Iniciando actividad con ADB: " + appPackage + "/" + appActivity);
            driver.executeScript("mobile: shell", Map.of(
                    "command", "am",
                    "args", List.of("start", "-n", appPackage + "/" + appActivity)
            ));

            System.out.println("[AppConfigScreen] Sesion Appium creada correctamente. SessionId: " + driver.getSessionId());
        } catch (MalformedURLException e) {
            throw new RuntimeException("[AppConfigScreen] URL de Appium invalida", e);
        }
    }

    public static AndroidDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException(
                    "[AppConfigScreen] El driver no ha sido inicializado. " +
                            "Debes llamar a iniciarAplicacion(...) antes de interactuar con la pantalla."
            );
        }
        return driver;
    }

    public void cerrarAplicacion(String appId) {

        if (driver == null) {
            System.out.println("[AppConfigScreen] No hay sesion activa para cerrar.");
            return;
        }

        try {
            System.out.println("[AppConfigScreen] Cerrando aplicacion: " + appId);
            driver.executeScript("mobile: terminateApp", Map.of("appId", appId));

        } finally {
            System.out.println("[AppConfigScreen] Cerrando sesion Appium...");
            driver.quit();
            driver = null;
            System.out.println("[AppConfigScreen] Sesion Appium cerrada correctamente.");
        }
    }

    public byte[] tomarScreenshot() {

        if (driver == null) {
            System.out.println("[AppConfigScreen] No hay sesion activa, no se puede tomar screenshot.");
            return new byte[0];
        }

        System.out.println("[AppConfigScreen] Tomando screenshot...");
        return driver.getScreenshotAs(OutputType.BYTES);
    }

    private Properties cargarPropiedades(String archivoConfiguracion) {
        Properties properties = new Properties();
        String resourcePath = "config/" + archivoConfiguracion;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourcePath)) {

            if (input == null) {
                throw new IllegalStateException(
                        "[AppConfigScreen] No se encontro el archivo de configuracion '" + resourcePath +
                                "' en src/test/resources/config"
                );
            }

            properties.load(input);
            return properties;

        } catch (IOException e) {
            throw new RuntimeException("[AppConfigScreen] Error leyendo el archivo de configuracion: " + resourcePath, e);
        }
    }

    private UiAutomator2Options construirOptions(Properties properties) {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName(properties.getProperty("platformName", "Android"));
        options.setAutomationName(properties.getProperty("automationName", "UiAutomator2"));
        options.setUdid(obtenerObligatorio(properties, "udid"));
        options.setDeviceName(obtenerObligatorio(properties, "deviceName"));
        options.setPlatformVersion(obtenerObligatorio(properties, "platformVersion"));
        options.setApp(obtenerObligatorio(properties, "app"));
        options.setAppPackage(obtenerObligatorio(properties, "appPackage"));

        if (properties.containsKey("appActivity")) {
            options.setAppActivity(properties.getProperty("appActivity"));
        }

        // Evitamos que intente lanzar la actividad por ADB automáticamente al iniciar la sesión
        options.setCapability("appium:noSign", true);
        options.setCapability("appium:autoLaunch", false);

        options.setNoReset(false);
        options.setAutoGrantPermissions(true);
        options.setCapability("appium:enforceAppInstall", true);
        options.setCapability("appium:disableWindowAnimation", true);

        long timeoutSeconds = Long.parseLong(properties.getProperty("newCommandTimeoutSeconds", "300"));
        options.setNewCommandTimeout(Duration.ofSeconds(timeoutSeconds));

        return options;
    }

    private String obtenerObligatorio(Properties properties, String key) {
        String value = properties.getProperty(key);
        return Objects.requireNonNull(value, "[AppConfigScreen] Falta la propiedad obligatoria '" + key + "'");
    }
}