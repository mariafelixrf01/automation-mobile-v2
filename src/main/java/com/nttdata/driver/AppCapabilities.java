package com.nttdata.driver;

import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppCapabilities {

    private final String appiumUrl = "http://127.0.0.1:4723/";
    private final UiAutomator2Options options;

    // Constructor requerido para el método estático from(...)
    public AppCapabilities(UiAutomator2Options options) {
        this.options = options;
    }

    public String getAppiumUrl() {
        return appiumUrl;
    }

    // Método para exponer las options construidas
    public UiAutomator2Options toUiAutomator2Options() {
        return this.options;
    }

    public static AppCapabilities from(String archivoConfiguracion) {
        Properties props = new Properties();
        String rutaResource = "config/" + archivoConfiguracion;

        try (InputStream input = AppCapabilities.class.getClassLoader().getResourceAsStream(rutaResource)) {
            if (input == null) {
                throw new IllegalStateException("No se encontró el archivo de configuración: " + rutaResource);
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de configuración: " + rutaResource, e);
        }

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(props.getProperty("platformName", "Android"));
        options.setAutomationName(props.getProperty("automationName", "UiAutomator2"));
        options.setUdid(props.getProperty("udid"));
        options.setDeviceName(props.getProperty("deviceName"));
        options.setPlatformVersion(props.getProperty("platformVersion"));

        if (props.containsKey("app")) {
            options.setApp(props.getProperty("app"));
        }

        options.setAppPackage(props.getProperty("appPackage"));
        options.setAppActivity(props.getProperty("appActivity"));

        // Capability clave para Android 15 y solucionar la SecurityException
        if (props.containsKey("appWaitActivity")) {
            options.setCapability("appium:appWaitActivity", props.getProperty("appWaitActivity"));
        } else {
            options.setCapability("appium:appWaitActivity", props.getProperty("appPackage") + ".*");
        }

        options.setNoReset(Boolean.parseBoolean(props.getProperty("noReset", "true")));
        options.setAutoGrantPermissions(Boolean.parseBoolean(props.getProperty("autoGrantPermissions", "true")));

        return new AppCapabilities(options);
    }
}