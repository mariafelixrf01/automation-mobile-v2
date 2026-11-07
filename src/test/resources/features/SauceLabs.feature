# language: es
@Smoke
Característica: Validación del carrito de compras en la app de SauceLabs

  Esquema del escenario: Validar agregar productos y actualizar el carrito
    Dado que estoy en la aplicación de SauceLabs
    Y valido que carguen correctamente los productos en la galeria
    Cuando agrego <UNIDADES> del siguiente producto "<PRODUCTO>"
    Then valido el carrito de compra actualice correctamente

    Ejemplos:
      | PRODUCTO                  | UNIDADES |
      | Sauce Labs Backpack       | 1        |
      | Sauce Labs Bolt - T-Shirt | 1        |
      | Sauce Labs Bike Light     | 2        |
