# language: es
@marketing
Característica: Asociación de leads generados a campañas de marketing

  Para medir el impacto comercial de cada acción de marketing,
  como responsable de marketing,
  los leads generados deben quedar asociados a la campaña que les dio origen
  junto con su información comercial y de contacto relevante.

  Escenario: Vinculación exitosa de lead a campaña
    Dado que existe una campaña activa denominada "Black Friday PSA 2025"
    Cuando "Laura Gómez" registra el lead "Tech Solutions S.R.L." con los siguientes datos:
      | campo            | valor                         |
      | campaña origen   | Black Friday PSA 2025         |
      | empresa          | Tech Solutions S.R.L.         |
      | canal origen     | LinkedIn                      |
      | email            | contacto@techsolutions.com    |
      | teléfono         | 11-5555-2222                  |
      | producto interés | PSA Soporte                   |
    Entonces debe establecerse el vínculo comercial entre la campaña y el lead
    Y el lead debe quedar registrado bajo el canal "LinkedIn" en estado "Nuevo"
    Y el lead debe quedar asociado al producto "PSA Soporte"
    Y la información de contacto del lead debe quedar registrada correctamente

  Esquema del escenario: Validación de campos obligatorios para el registro de leads
    Dado que "Laura Gómez" intenta registrar un lead para la campaña "Black Friday PSA 2025"
    Cuando se omite el dato obligatorio "<campo_faltante>"
    Entonces el registro debe ser rechazado por falta de información mandatoria
    Y no debe crearse ningún vínculo con la campaña

    Ejemplos:
      | campo_faltante  |
      | campaña origen  |
      | canal origen    |
      | producto interés |
      | email           |