# language: es
@marketing
Característica: Gestión del ciclo de vida del lead en el pipeline de marketing
  Para asegurar la integridad y trazabilidad del proceso de captación,
  Como ejecutivo de Marketing,
  Quiero que los leads avancen secuencialmente a través de las etapas definidas del pipeline de marketing.

   Esquema del escenario: Validación de la progresión secuencial en el pipeline
     Dado que el lead "Tech Solutions S.R.L." se encuentra actualmente en la etapa "<etapa_actual>"
     Cuando se intenta avanzar el lead a la etapa "<etapa_siguiente>"
     Entonces el lead debe avanzar a la etapa "<etapa_siguiente>"
     Y el lead debe quedar clasificado formalmente en la etapa "<etapa_siguiente>"
     Ejemplos:
       | etapa_actual | etapa_siguiente               |
       | Nuevo        | Contactado                    |
       | Contactado   | Calificado                    |
       | Calificado   | Convertido en Oportunidad     |

  Esquema del escenario: Restricción de saltos de etapa no permitidos
    Dado que el lead "Tech Solutions S.R.L." se encuentra en la etapa "<etapa_actual>"
    Cuando se intenta saltar directamente a la etapa "<etapa_destino>"
    Entonces la transición debe ser rechazada por violar la secuencia del pipeline
    Y el lead debe permanecer en su etapa original "<etapa_actual>"
    Ejemplos:
      | etapa_actual | etapa_destino                |
      | Nuevo        | Calificado                   |
      | Nuevo        | Convertido en Oportunidad    |
      | Contactado   | Convertido en Oportunidad    |
      | Calificado   | Perdido                      |

  Escenario: Clasificación inicial de un lead captado
    Dado que existe una campaña activa denominada "Campaña Primavera 2026"
    Y un contacto interesado llamado "Juan Pérez"
    Cuando se crea un lead asociado a dicha campaña para la empresa "Empresa XYZ"
    Entonces el lead debe ser dado de alta exitosamente
    Y su estado inicial debe ser definido como "Nuevo"

  Escenario: Cierre de campaña con conversión exitosa
    Dado que el lead "Tech Solutions S.R.L." ha alcanzado satisfactoriamente la etapa "Calificado"
    Cuando se confirma la conversión del lead a Oportunidad
    Entonces el estado del lead debe cambiar a "Convertido en Oportunidad"
    Y la conversión debe quedar vinculada a la campaña "Black Friday PSA 2025"

  Escenario: Registro de descarte de lead para análisis de causa raíz
    Dado que el lead "Tech Solutions S.R.L." se encuentra en la etapa "Calificado"
    Cuando se marca el lead como "Perdido" indicando el motivo "Fuera del segmento objetivo"
    Entonces el estado del lead debe cambiar a "Perdido"
    Y el lead debe conservar su estado final registrado

  Escenario: Impedimento de cierre sin definición de resultado final
    Dado que el lead "Tech Solutions S.R.L." está listo para finalizar su ciclo en la etapa "Calificado"
    Cuando se intenta completar el proceso sin registrar un resultado (Convertido en Oportunidad o Perdido)
    Entonces el avance debe bloquearse hasta identificar el resultado final de la calificación

  Escenario: Listado de leads con filtro por campaña
    Dado que se han registrado los siguientes leads:
      | nombre       | campaña               | canal  |
      | Juan Pérez   | Campaña Primavera     | Email  |
      | María López  | Campaña Primavera     | Email  |
      | Pedro García | Campaña Otoño         | Email  |
    Cuando se solicita el listado de leads filtrado por campaña "Campaña Primavera"
    Entonces deben obtenerse 2 leads en el resultado
    Y los leads deben pertenecer a la campaña "Campaña Primavera"

  Escenario: Obtención de detalle de un lead existente
    Dado que se ha registrado un lead llamado "Juan Pérez" para la campaña "Campaña Primavera"
    Cuando se solicita el detalle del lead
    Entonces debe devolverse la información completa del lead
    Y el lead debe estar en etapa "Nuevo"
    Y el lead debe estar asociado a la campaña "Campaña Primavera"