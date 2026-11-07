package com.nttdata.driver;

import com.nttdata.screens.AppConfigScreen;
import io.appium.java_client.android.AndroidDriver;

public class AppiumDriverManager {

    private static AppiumDriverManager instance;

    private AppiumDriverManager() {
        // Constructor privado para patrón Singleton
    }

    public static synchronized AppiumDriverManager getInstance() {
        if (instance == null) {
            instance = new AppiumDriverManager();
        }
        return instance;
    }

    /**
     * Inicia la aplicación utilizando AppConfigScreen por debajo
     */
    public void startApp(AppCapabilities capabilities) {
        // AppConfigScreen maneja la inicialización mediante el archivo properties
    }

    /**
     * Retorna el driver activo obtenido desde AppConfigScreen
     */
    public static AndroidDriver getDriver() {
        // Delega directamente en AppConfigScreen para obtener la instancia activa del driver
        return AppConfigScreen.getDriver();
    }

    public void stopApp(String appId) {
        try {
            AppConfigScreen configScreen = new AppConfigScreen();
            configScreen.cerrarAplicacion(appId);
        } catch (Exception e) {
            System.out.println("[AppiumDriverManager] Error al cerrar la app: " + e.getMessage());
        }
    }
}