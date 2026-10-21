# language: es
@marketing
Característica: Cálculo del ROI de campañas de marketing

  Para tomar decisiones basadas en datos sobre la rentabilidad de las acciones comerciales,
  como responsable de marketing,
  debe calcularse el retorno de inversión de cada campaña vinculando sus costos
  con los ingresos generados por los leads convertidos.

  Escenario: Cálculo de ROI en una campaña con leads convertidos
    Dado que existe una campaña activa denominada "Lanzamiento PSA 2026" para métricas
    Y la campaña tiene un costo de 100000
    Y se registran los siguientes leads para métricas:
      | nombre       | etapa    |
      | Juan Pérez   | Ganado   |
      | María López  | Ganado   |
      | Pedro García | Perdido  |
    Cuando se consultan las métricas de la campaña
    Entonces la respuesta debe incluir los campos "roi", "leadsGenerados", "leadsConvertidos", "conversion" y "montoGanadoTotal"
    Y la cantidad de leads generados debe ser 3
    Y la cantidad de leads convertidos debe ser 2

  Escenario: Métricas de campaña sin leads registrados
    Dado que existe una campaña activa denominada "Campaña Vacía 2026" para métricas
    Y la campaña no tiene leads asociados
    Cuando se consultan las métricas de la campaña
    Entonces la respuesta debe indicar 0 leads generados
    Y la respuesta debe indicar 0 leads convertidos
    Y la tasa de conversión debe ser 0

  Escenario: Métricas de campaña sin leads convertidos
    Dado que existe una campaña activa denominada "Campaña Sin Conversión 2026" para métricas
    Y se registran los siguientes leads para métricas:
      | nombre          | etapa   |
      | Ana Torres      | Perdido |
      | Carlos Sánchez  | Perdido |
    Cuando se consultan las métricas de la campaña
    Entonces la cantidad de leads generados debe ser 2
    Y la cantidad de leads convertidos debe ser 0
    Y la tasa de conversión debe ser 0
