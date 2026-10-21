# language: es
Característica: Health del módulo
  Como docente o integrante del squad
  Quiero un escenario ejecutable mínimo
  Para verificar el scaffold inicial

  Escenario: El backend responde ok
    Cuando consulto el endpoint de health
    Entonces recibo una respuesta ok

  Escenario: La documentación OpenAPI responde
    Cuando consulto la documentación OpenAPI
    Entonces recibo una documentación OpenAPI ok

  Escenario: La interfaz Swagger UI responde
    Cuando consulto la interfaz Swagger UI
    Entonces recibo una interfaz Swagger UI ok
