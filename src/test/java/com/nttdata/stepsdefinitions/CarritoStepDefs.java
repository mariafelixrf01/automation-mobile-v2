package com.nttdata.stepsdefinitions;

import com.nttdata.steps.SauceLabsCatalogSteps;
import com.nttdata.support.ScreenshotAttacher;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class CarritoStepDefs {

    private final SauceLabsCatalogSteps catalogSteps = new SauceLabsCatalogSteps();
    private Scenario scenario;

    @Before
    public void configurarScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @And("valido que carguen correctamente los productos en la galeria")
    public void valido_que_carguen_correctamente_los_productos_en_la_galeria() {
        boolean cargado = catalogSteps.validarGaleriaCargada();
        assertTrue("La galería de productos no cargó correctamente", cargado);
        ScreenshotAttacher.attach(scenario, "Galeria de productos cargada");
    }

    @When("agrego {int} del siguiente producto {string}")
    public void agrego_del_siguiente_producto(Integer unidades, String producto) {
        catalogSteps.agregarProductoConUnidades(producto, unidades);
        ScreenshotAttacher.attach(scenario, "Producto agregado - " + producto + " (" + unidades + " unds)");
    }

    @Then("valido el carrito de compra actualice correctamente")
    public void valido_el_carrito_de_compra_actualice_correctamente() {
        boolean actualizado = catalogSteps.validarCarritoActualizado();
        assertTrue("El carrito de compras no se actualizó correctamente", actualizado);
        ScreenshotAttacher.attach(scenario, "Carrito de compras actualizado");
    }
}