package com.nttdata.steps;

import com.nttdata.driver.AppCapabilities;
import com.nttdata.driver.AppiumDriverManager;
import com.nttdata.screens.SauceLabsCatalogScreen;

public class SauceLabsCatalogSteps {

    private static final String ARCHIVO_CONFIGURACION = "saucelabs.properties";

    private final SauceLabsCatalogScreen screen = new SauceLabsCatalogScreen();

    public void iniciarAplicacion() {
        AppCapabilities capabilities = AppCapabilities.from(ARCHIVO_CONFIGURACION);
        AppiumDriverManager.getInstance().startApp(capabilities);
    }

    public boolean validarGaleriaCargada() {
        return screen.validarGaleriaCargada();
    }

    public void agregarProductoConUnidades(String producto, int unidades) {
        screen.seleccionarProducto(producto);
        if (unidades > 1) {
            screen.agregarUnidades(unidades);
        }
        screen.hacerClicEnAddToCart();
    }

    public boolean validarCarritoActualizado() {
        return screen.validarCarritoActualizado();
    }
}