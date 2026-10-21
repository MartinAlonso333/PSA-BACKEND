# language: es
@marketing
Característica: Consulta de catálogos de productos y canales

  Para conocer la oferta disponible y los medios de captación,
  como responsable de marketing,
  quiero consultar el catálogo de productos y los canales disponibles.

  Escenario: Listado de productos disponibles
    Dado que existen productos registrados en el sistema
    Cuando se solicita el listado de productos
    Entonces deben devolverse todos los productos disponibles
    Y cada producto debe incluir su nombre y código

  Escenario: Obtención de detalle de un producto
    Dado que existe un producto con nombre "CRM Básico BDD"
    Cuando se solicita el detalle del producto "CRM Básico BDD"
    Entonces debe devolverse la información del producto
    Y debe incluir su nombre y código

  Escenario: Listado de canales disponibles
    Dado que existen canales registrados en el sistema
    Cuando se solicita el listado de canales
    Entonces deben devolverse todos los canales disponibles

  Escenario: Obtención de detalle de un canal
    Dado que existe un canal con nombre "Email BDD"
    Cuando se solicita el detalle del canal "Email BDD"
    Entonces debe devolverse la información del canal
