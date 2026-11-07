# language: es
Característica: Validación del carrito de compras en la aplicación de SauceLabs

  Como usuario de la aplicación de SauceLabs
  Quiero agregar productos con sus respectivas unidades a la galería
  Para validar que el carrito de compras se actualice correctamente

  @automation-mobile @carrito
  Esquema del escenario: Validar la adicion y actualizacion del carrito de compras para diferentes productos
    Dado ingreso al aplicativo "saucelabs"
    Y valido que carguen correctamente los productos en la galeria
    Cuando agrego <UNIDADES> del siguiente producto "<PRODUCTO>"
    Entonces valido el carrito de compra actualice correctamente
    Y cierro el aplicativo "com.swaglabsmobileapp"

    Ejemplos:
      | PRODUCTO                  | UNIDADES |
      | Sauce Labs Backpack       | 1        |
      | Sauce Labs Bolt - T-Shirt | 1        |
      | Sauce Labs Bike Light     | 2        |