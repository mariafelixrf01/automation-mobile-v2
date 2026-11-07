package com.nttdata.driver;

import io.appium.java_client.android.options.UiAutomator2Options;

public class AppCapabilities {

    private final String appiumUrl = "http://127.0.0.1:4723/wd/hub"; // O la URL de tu servidor Appium

    public String getAppiumUrl() {
        return appiumUrl;
    }

    public UiAutomator2Options toUiAutomator2Options() {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setDeviceName("emulator-5554"); // Cambia por el nombre de tu emulador
        options.setAutomationName("UiAutomator2");

        // Ruta absoluta de tu APK descargado de Sauce Labs
        options.setApp("C:\\platform-tools\\mda-2.0.2-23.apk");

        options.setAppPackage("com.saucelabs.mydemoapp.android");
        options.setAppActivity("com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
        options.setNoReset(false);

        return options;
    }
}