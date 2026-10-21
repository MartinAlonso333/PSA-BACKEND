# language: es
@ventas
Característica: Gestión de estados del lead en el ciclo comercial

  Como ejecutivo de ventas
  Quiero actualizar el progreso del lead
  Para asegurar un seguimiento comercial preciso y una correcta conversión a oportunidades

  Escenario: Clasificación inicial de un lead captado
    Dado que existe una campaña activa denominada "Campaña Primavera 2026"
    Y un contacto interesado llamado "Juan Pérez"
    Cuando se crea un lead asociado a dicha campaña para la empresa "Empresa XYZ"
    Entonces el lead debe ser dado de alta exitosamente
    Y su estado inicial debe ser definido como "Nuevo"

  Esquema del escenario: Avance controlado por etapas secuenciales
    Dado que un lead se encuentra actualmente en la etapa <estado_actual>
    Cuando el ejecutivo intenta mover el lead a la etapa <estado_siguiente>
    Entonces el sistema debe validar la transición y registrar el cambio con fecha actual
    Y el lead debe quedar clasificado como <estado_siguiente>

    Ejemplos:
      | estado_actual | estado_siguiente |
      | Contacto     | Análisis          |
      | Análisis     | Consultoría       |
      | Consultoría  | Demo              |
      | Demo         | Presupuesto       |
      | Presupuesto  | Cierre            |
  Esquema del escenario: Impedimento de saltos de etapa en el flujo comercial
    Dado que un lead se encuentra en el estado <estado_actual>
    Cuando el ejecutivo intenta saltar directamente al estado <estado_destino>
    Entonces la acción debe ser bloqueada por violar la secuencia del pipeline
    Y el lead debe permanecer en su estado original <estado_actual>

    Ejemplos:
      | estado_actual | estado_destino|
      | Contacto     | Demo           |
      | Contacto     | Cierre         |
      | Análisis     | Presupuesto    |

  Escenario: Generación de oportunidad por calificación positiva
    Dado que el lead de "Tech Solutions S.R.L." ha alcanzado el estado "Calificado"
    Cuando el ejecutivo confirma la transición al estado "Convertido"
    Entonces se debe generar automáticamente una oportunidad comercial vinculada
    Y el sistema debe preservar el historial completo de la evolución del lead

  Escenario: Mantenimiento de la atribución de origen durante el ciclo
    Dado que un lead fue captado originalmente por la campaña "Campaña Otoño 2026"
    Cuando el lead transita por las diversas etapas de calificación y contacto
    Entonces el sistema debe permitir visualizar en todo momento que su origen es "Campaña Otoño 2026"