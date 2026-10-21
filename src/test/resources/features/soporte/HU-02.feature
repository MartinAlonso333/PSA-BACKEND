# language: es
@soporte
Característica: Clasificación de tickets y asignación de SLA

  Como agente de soporte
  Quiero clasificar un ticket con una celeridad al momento de su creación
  Para garantizar que cada caso tenga un SLA definido
  Esquema del escenario: Determinación automática del tiempo de resolución según celeridad
    Dado que el agente está creando un ticket de soporte para el cliente "Empresa Alfa S.A."
    Cuando se asigna la prioridad de atención en nivel <celeridad>
    Entonces el sistema debe calcular automáticamente el plazo de resolución en <dias> días
    Y dicha fecha límite debe quedar vinculada al ciclo de vida del ticket

    Ejemplos:
      | celeridad | dias |
      | 1         | 1    |
      | 2         | 7    |
      | 3         | 30   |

  Escenario: Restricción de creación sin definición de prioridad
    Dado que el agente "Carlos Medina" ha completado los datos de descripción y canal
    Cuando intenta guardar el ticket sin haber seleccionado un nivel de celeridad
    Entonces la creación del ticket debe ser bloqueada para asegurar que el caso tenga un SLA asignado
    Y el sistema no debe permitir que el ticket entre en el pipeline de resolución