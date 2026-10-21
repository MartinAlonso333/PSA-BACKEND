# language: es
@soporte
Característica: Escalación formal de tickets entre áreas

  Como agente de soporte
  Quiero escalar formalmente un ticket a Desarrollo o Implementaciones
  Para resolver casos que superan el nivel inicial de atención

  Esquema del escenario: Transferencia de responsabilidad a un área técnica
    Dado que el ticket "TKT-0042" se encuentra en estado "Abierto" bajo la órbita de Soporte
    Cuando el agente "Carlos Medina" escala el caso al área de <area> por "Intervención técnica requerida"
    Entonces el ticket debe quedar asignado formalmente al equipo de <area>
    Y su estado debe cambiar a "Escalado" para reflejar el cambio de nivel de atención
    Y se debe registrar la fecha, el motivo y el área destino en el log de auditoría

    Ejemplos:
      | area              |
      | Desarrollo        |
      | Implementaciones  |

  Escenario: Restricción de escalación para áreas no autorizadas
    Dado que el ticket "TKT-0042" ya ha sido escalado y está asignado a Desarrollo
    Cuando un integrante de Desarrollo intenta realizar una nueva escalación
    Entonces el sistema debe impedir la acción para garantizar que solo Soporte gestione el flujo de niveles
    Y el ticket debe permanecer bajo la responsabilidad de Desarrollo

  Escenario: Jerarquía de escalación obligatoria para alcanzar Dirección
    Dado que el ticket "TKT-0055" aún se encuentra en nivel de soporte inicial ("Abierto")
    Cuando el agente intenta transferir el ticket directamente al área de Dirección
    Entonces la escalación debe ser bloqueada por incumplimiento del protocolo jerárquico
    Y se debe informar que el caso requiere una escala técnica previa

  Escenario: Integridad de la trazabilidad en el historial de escalaciones
    Dado que el ticket "TKT-0042" fue escalado exitosamente por "Carlos Medina" a "Desarrollo"
    Cuando el equipo técnico consulta la línea de tiempo del ticket
    Entonces se debe visualizar el registro detallado de la transición:
      | campo         | valor                        |
      | área destino  | Desarrollo                   |
      | motivo        | Requiere intervención técnica|
      | responsable   | Carlos Medina                |