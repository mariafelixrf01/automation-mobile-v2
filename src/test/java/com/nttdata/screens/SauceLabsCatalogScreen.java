package com.nttdata.screens;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SauceLabsCatalogScreen extends BaseScreen {

    private final By tituloProductos = AppiumBy.accessibilityId("test-PRODUCTS");
    private final By btnPlus = AppiumBy.accessibilityId("test-Plus");
    private final By btnAddToCart = AppiumBy.accessibilityId("test-ADD TO CART");
    private final By cartBadge = AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Cart']//android.widget.TextView");

    public boolean validarGaleriaCargada() {
        System.out.println("[SauceLabsCatalogScreen] Validando carga de la galería de productos...");
        WebElement titulo = waitForVisible(tituloProductos);
        return titulo.isDisplayed();
    }

    public void seleccionarProducto(String producto) {
        System.out.println("[SauceLabsCatalogScreen] Seleccionando producto: " + producto);
        By productoLocator = AppiumBy.xpath("//android.widget.TextView[@text=\"" + producto + "\"]");
        WebElement elementoProducto = waitForVisible(productoLocator);
        elementoProducto.click();
    }

    public void agregarUnidades(int unidades) {
        System.out.println("[SauceLabsCatalogScreen] Configurando cantidad de unidades: " + unidades);
        for (int i = 1; i < unidades; i++) {
            WebElement plus = waitForVisible(btnPlus);
            plus.click();
        }
    }

    public void hacerClicEnAddToCart() {
        System.out.println("[SauceLabsCatalogScreen] Haciendo clic en 'ADD TO CART'...");
        WebElement addBtn = waitForVisible(btnAddToCart);
        addBtn.click();
    }

    public boolean validarCarritoActualizado() {
        System.out.println("[SauceLabsCatalogScreen] Validando actualización del carrito de compras...");
        WebElement badge = waitForVisible(cartBadge);
        String cantidadStr = badge.getText();
        System.out.println("[SauceLabsCatalogScreen] Cantidad en el carrito detectada: " + cantidadStr);
        return !cantidadStr.isEmpty() && Integer.parseInt(cantidadStr) > 0;
    }
}