# R5.2 - Diccionario de Datos

## Alcance del diccionario

- Este diccionario define los términos principales utilizados en el módulo de Marketing del sistema PSA.
- Incluye conceptos relacionados con campañas, generación de leads y su vinculación con el proceso comercial.
- Su objetivo es unificar el lenguaje entre las areas de Marketing y Ventas.

## Términos principales

| Término | Definición | Fuente | Observaciones |
| --- | --- | --- | --- |
| Canal | Medio de comunicación utilizado para ejecutar campañas o captar leads | Reunión con Marketing | Ej: redes sociales, email marketing, formularios, eventos |
| Campaña | Acción de marketing orientada a generar leads y oportunidades comerciales | Reunión con Marketing | Puede ejecutarse en distintos canales |
| Campaña planificada | Campaña creada pero aún no iniciada | Reunión con Marketing | Estado inicial de campaña |
| Campaña activa | Campaña que se encuentra en ejecución | Reunión con Marketing | Puede generar leads en tiempo real |
| Campaña finalizada | Campaña cerrada y sin captación activa de leads | Reunión con Marketing | Estado final de campaña |
| Lead | Persona o empresa interesada en productos o servicios | Reunión con Marketing | Debe registrar empresa, contacto y campaña de origen |
| Origen del lead | Fuente específica que generó un lead | Reunión con Marketing | Puede corresponder a campaña o canal |
| Estado del lead | Situación actual del lead dentro del proceso comercial | Reunión con Marketing | Estados básicos definidos para el MVP |
| Lead nuevo | Lead recién registrado en el sistema | Reunión con Marketing | Primer estado del lead |
| Lead contactado | Lead que ya recibió un contacto inicial | Reunión con Marketing | Estado intermedio |
| Lead calificado | Lead validado como potencial oportunidad comercial | Reunión con Marketing | Puede derivar en oportunidad o en perdido |
| Lead perdido | Lead que no se convierte en una oportunidad comercial | Reunión con Product Owner | Último estado del lead |
| Lead convertido | Lead transformado en oportunidad comercial | Reunión con Marketing | Último estado del lead |
| ROI | Indicador de rentabilidad de una campaña | Reunión con Marketing | Considera costos de campaña e ingresos asociados |
| Empresa | Persona jurídica que representa a uno o más leads | Reunión con Marketing | Atributos: Nombre, RazonSocial, CUIT, Direccion |
| Conversión de leads | Métrica que mide cuántos leads avanzan dentro del proceso comercial | Reunión con Marketing | Permite evaluar efectividad |
| Leads por campaña | Métrica que contabiliza leads generados por cada campaña | Reunión con Marketing | Indicador básico de rendimiento |
| Efectividad de campaña | Relación entre campañas y resultados obtenidos | Reunión con Marketing | Utilizada para análisis comercial |
| Trazabilidad comercial | Capacidad de seguir el recorrido de un lead desde su origen | Reunión con Marketing | Vincula campañas, leads y resultados |
| Métricas de marketing | Indicadores utilizados para evaluar campañas | Reunión con Marketing | Incluye ROI, conversión y leads generados |

## Inconsistencias o ambigüedades resueltas

- Se transformó el atributo `Canal` del modelo en una entidad.
- Se eliminó la entidad `Métrica` del modelo: las métricas se modelan como atributos derivados en `Campaña`
- La conversión de lead a oportunidad ocurre en el módulo de Ventas, no en Marketing