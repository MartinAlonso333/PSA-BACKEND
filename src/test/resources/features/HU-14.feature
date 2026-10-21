# language: es
@marketing
Característica: Visualización de métricas de efectividad de campañas y conversión de leads

  Para evaluar el rendimiento comercial de las acciones de marketing,
  como responsable de marketing,
  deben visualizarse métricas de efectividad que incluyan la cantidad de leads generados,
  la tasa de conversión y los leads convertidos por campaña.

  Escenario: Visualización de métricas con leads en múltiples estados
    Dado que existe una campaña activa denominada "Campaña Mixta 2026" para métricas
    Y se registran los siguientes leads para métricas:
      | nombre         | etapa    |
      | Empresa Alpha  | Ganado   |
      | Empresa Beta   | Ganado   |
      | Empresa Gamma  | Perdido  |
      | Empresa Delta  | Nuevo    |
      | Empresa Epsilon| Contactado |
    Cuando se consultan las métricas de la campaña
    Entonces la cantidad de leads generados debe ser 5
    Y la cantidad de leads convertidos debe ser 2
    Y la tasa de conversión debe ser 40

  Escenario: Visualización de métricas con conversión completa
    Dado que existe una campaña activa denominada "Campaña Perfecta 2026" para métricas
    Y se registran los siguientes leads para métricas:
      | nombre          | etapa    |
      | Soluciones XYZ  | Ganado   |
      | Tech Solutions  | Ganado   |
    Cuando se consultan las métricas de la campaña
    Entonces la cantidad de leads generados debe ser 2
    Y la cantidad de leads convertidos debe ser 2
    Y la tasa de conversión debe ser 100

  Escenario: Visualización de métricas sin leads
    Dado que existe una campaña activa denominada "Campaña Sin Actividad 2026" para métricas
    Y la campaña no tiene leads asociados
    Cuando se consultan las métricas de la campaña
    Entonces la respuesta debe contener el identificador de la campaña
    Y la cantidad de leads generados debe ser 0
    Y la cantidad de leads convertidos debe ser 0
    Y la tasa de conversión debe ser 0
