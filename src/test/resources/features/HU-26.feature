# language: es
@marketing
Característica: Gestión de empresas representadas por los leads

  Para mantener actualizados los datos corporativos de las empresas vinculadas a los leads,
  como responsable de marketing,
  quiero consultar y registrar empresas en el sistema.

  Escenario: Listado de empresas registradas
    Dado que existen empresas registradas en el sistema
    Cuando se solicita el listado de empresas
    Entonces deben devolverse todas las empresas disponibles

  Escenario: Obtención de detalle de una empresa
    Dado que existe una empresa con nombre "TechCorp"
    Cuando se solicita el detalle de la empresa "TechCorp"
    Entonces debe devolverse la información completa de la empresa
    Y debe incluir su razón social, CUIT y dirección

  Escenario: Registro de una nueva empresa
    Dado que se desea registrar una nueva empresa
    Cuando se crea la empresa con los siguientes datos:
      | campo        | valor                  |
      | nombre       | Nueva Empresa S.A.     |
      | razón social | Nueva Empresa S.A.     |
      | cuit         | 30-99999999-9          |
      | dirección    | Calle Falsa 123        |
    Entonces la empresa debe ser creada exitosamente
    Y debe quedar disponible en el listado de empresas
