# language: es
@soporte
Característica: Registro omnicanal de tickets de soporte

  Como agente de soporte
  Quiero registrar un ticket desde cualquier canal de comunicación
  Para asegurar trazabilidad centralizada de cada requerimiento del cliente

  Escenario: Registro exitoso de requerimiento por correo electrónico
    Dado que el cliente "Empresa Alfa S.A." ha reportado un incidente
    Y el agente "Carlos Medina" está documentando el caso
    Cuando se registra el ticket especificando el canal "email", la descripción "Error al generar factura" y celeridad 2
    Entonces el requerimiento debe quedar formalmente registrado bajo la responsabilidad de "Carlos Medina"
    Y el ticket debe iniciar su ciclo de vida en estado "Abierto"

  Esquema del escenario: Validación de registro desde múltiples puntos de contacto
    Dado que el cliente "Empresa Alfa S.A." reportó un problema por el canal <canal>
    Cuando el agente completa el registro con la descripción y celeridad correspondientes
    Entonces el sistema debe generar un nuevo registro de soporte vinculado a dicho canal
    Y el ticket debe quedar disponible para su gestión inmediata

    Ejemplos:
      | canal      |
      | email      |
      | teléfono   |
      | formulario |

  Esquema del escenario: Integridad de datos obligatorios en la creación de tickets
    Dado que el agente "Carlos Medina" está registrando un nuevo incidente
    Cuando intenta finalizar el alta omitiendo el dato obligatorio <campo_faltante>
    Entonces el registro debe ser rechazado por falta de información mandatoria
    Y no se debe crear ningún ticket nuevo en el sistema

    Ejemplos:
      | campo_faltante |
      | celeridad      |
      | cliente        |

  Escenario: Obligatoriedad de registro antes de finalizar atención
    Dado que el agente "Carlos Medina" atendió una llamada de "Empresa Alfa S.A."
    Cuando intenta cerrar la interacción telefónica sin haber generado un ticket de soporte
    Entonces el sistema debe bloquear el cierre de la interacción para garantizar la trazabilidad del contacto