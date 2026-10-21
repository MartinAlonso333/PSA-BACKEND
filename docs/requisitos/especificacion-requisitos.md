# R4 - Especificación de Requisitos
## Objetivo y alcance

### Sistema a construir

- Sistema integral de gestión empresarial para PSA que centralice la información de todas las áreas, permitiendo mejorar la toma de decisiones basada en datos, la eficiencia operativa y la comunicación interna.
- El sistema será utilizado inicialmente de forma interna por PSA y, en caso de éxito, será comercializado como producto.

### Alcance inicial

- Desarrollo de los módulos:
  - Proyectos
  - Implementaciones
  - Soporte
  - Marketing
  - Ventas / CRM
  - Horas y Rentabilidad
- Integración entre módulos para asegurar flujo de información continuo.
- Centralización de tickets y comunicaciones con clientes.
- Estandarización de procesos internos (proyectos, soporte, marketing).
- Registro y seguimiento de campañas de marketing y leads.
- Métricas básicas de rendimiento (ROI, tiempos de resolución, estado de proyectos).
- Trazabilidad entre campañas, oportunidades, proyectos, horas y facturación.

### Fuera de alcance

- Automatización avanzada con IA (queda como línea futura).
- Funcionalidades avanzadas de Finanzas y Recursos Humanos en primera versión.

### Supuestos relevantes

- Los usuarios adoptarán el sistema como herramienta central de trabajo.
- Toda interacción con clientes será registrada mediante el sistema.
- Los módulos deben compartir información entre sí.
- PSA cuenta con infraestructura cloud para desplegar el sistema.
- Se dispondrá de acceso a stakeholders para validación continua de requisitos.

## Requisitos funcionales

| ID | Descripción | Valor o justificación | Evidencia o fuente |
| --- | --- | --- | --- |
| RF-01 | Centralizar la gestión de tickets de soporte en un único sistema | Mejora trazabilidad y evita pérdida de información | Minuta Usuarios PSA |
| RF-02 | Permitir derivación de tickets entre Soporte, Desarrollo e Implementaciones | Mejora coordinación entre áreas | Minuta Usuarios PSA |
| RF-03 | Registrar y gestionar proyectos con definición de alcance, roles y riesgos | Estandariza la gestión de proyectos | Minuta Usuarios PSA |
| RF-04 | Unificar metodología de trabajo entre Proyectos e Implementaciones | Reduce inconsistencias operativas | Minuta Usuarios PSA |
| RF-05 | Registrar campañas de marketing y vincularlas con leads | Permite medir efectividad de campañas | Minuta Usuarios PSA |
| RF-06 | Gestionar el ciclo de vida de leads (lead → cierre) | Mejora captación de clientes | Minuta Usuarios PSA / CEO |
| RF-07 | Medir ROI de campañas de marketing | Permite toma de decisiones basada en datos | Minuta Usuarios PSA |
| RF-08 | Integrar los módulos para compartir información en tiempo real | Evita silos de información | Minuta Presentación de visión |
| RF-09 | Registrar métricas de soporte (tiempos de resolución, SLA/celeridades) | Mejora calidad del servicio | Minuta Usuarios PSA |
| RF-10 | Permitir seguimiento del estado de proyectos y alertas de riesgos | Reduce fallas y atrasos | Minuta Presentación de visión |
| RF-11 | Permitir la carga de horas por tarea y día | Necesario para métricas de costos y rentabilidad | Minuta 2026-04-30 |
| RF-12 | Generar reportes de ingresos vs costos por producto y proyecto | Necesario para análisis de rentabilidad | Minuta 2026-04-30 |
| RF-13 | Permitir registrar estados detallados del lead y oportunidades comerciales | Mejora el seguimiento del pipeline comercial | Minuta Marketing y Ventas |
| RF-14 | Permitir asociar productos o servicios específicos a campañas de marketing | Facilita estrategias comerciales y análisis de efectividad | Minuta Usuarios PSA |
| RF-15 | Generar métricas de conversión y efectividad de campañas | Permite evaluar desempeño comercial y captación | Minuta Marketing y Ventas |
| RF-16 | Permitir asociar múltiples productos o servicios a una oportunidad comercial | Refleja propuestas comerciales complejas | Minuta Marketing y Ventas |
| RF-17 | Transferir automáticamente oportunidades ganadas al módulo de Implementaciones | Garantiza continuidad operativa y trazabilidad | Minuta Marketing y Ventas |
| RF-18 | Generar alertas ante riesgos de incumplimiento de SLA o celeridades | Reduce multas y escalaciones de clientes | Minuta PM-PO-JP |
| RF-19 | Registrar licencias y ausencias de colaboradores | Permite mejorar cálculo de disponibilidad y costos | Minuta PM-PO-JP |
| RF-20 | Centralizar la comunicación y derivación entre áreas dentro del sistema | Reduce pérdida de información y mejora trazabilidad | Minuta Usuarios PSA |
| RF-21 | Mantener trazabilidad entre campañas, ventas, proyectos, horas y facturación | Permite análisis integral de rentabilidad y operación | Minuta PM-PO-JP |
| RF-22 | Registrar y gestionar los estados de las campañas (Planificada, Activa, Finalizada) | Control del ciclo de vida de las campañas de marketing | Minuta 2026-05-07 (Marketing y Ventas) — Hallazgos |
| RF-23 | Registrar y gestionar los estados de los leads (Nuevo, Contactado, Calificado, Convertido en oportunidad, Perdido) | Seguimiento comercial estructurado desde la captación hasta la conversión | Minuta 2026-05-07 (Marketing y Ventas) — Hallazgos |
| RF-24 | Registrar el canal de origen obligatorio de cada lead | Permite analizar qué canales generan mejores resultados comerciales | Minuta 2026-05-07 (Marketing y Ventas) — Hallazgos, RN-07 |

## Requisitos no funcionales

| ID | Categoría | Descripción | Criterio de verificación | Evidencia o fuente |
| --- | --- | --- | --- | --- |
| RNF-01 | Usabilidad | El sistema debe ser accesible desde múltiples dispositivos (web responsive) | Pruebas en distintos dispositivos | Minuta Presentación de visión |
| RNF-02 | Performance | El sistema debe permitir acceso y actualización en tiempo real entre módulos | Tests de tiempos de respuesta | Minuta CEO PSA |
| RNF-03 | Escalabilidad | El sistema debe soportar crecimiento de usuarios y clientes | Pruebas de carga | Minuta CEO PSA |
| RNF-04 | Seguridad | Protección de datos y control de accesos por roles. En el MVP esta funcionalidad se limita al registro básico; los controles avanzados (permisos granulares, roles, auditoría de accesos) quedan fuera de alcance | Auditorías de seguridad | Minuta Presentación de visión, Minuta 2026-05-07 (Marketing y Ventas) — Hallazgos |
| RNF-05 | Disponibilidad | El sistema debe estar disponible en entorno cloud con alta disponibilidad | Monitoreo uptime | Minuta Presentación de visión |
| RNF-06 | Interoperabilidad | Los módulos deben comunicarse entre sí sin fricción | Pruebas de integración | Minuta Usuarios PSA |
| RNF-07 | Trazabilidad | El sistema debe conservar historial y trazabilidad entre módulos y entidades relacionadas | Validación de relaciones y auditoría funcional | Minuta Marketing y Ventas |
| RNF-08 | Modularidad | El sistema debe permitir evolución independiente de módulos funcionales | Revisión arquitectónica | Minuta CEO PSA |
| RNF-09 | Integrabilidad | El sistema debe permitir futuras integraciones con CRM, automatizaciones y servicios externos | Pruebas de integración futura | Minuta Marketing y Ventas |

## Reglas de negocio

| ID | Regla | Impacto en el sistema | Evidencia o fuente |
| --- | --- | --- | --- |
| RN-01 | Toda interacción con clientes debe generar un ticket en el sistema | Obliga centralización del soporte | Minuta Usuarios PSA |
| RN-02 | Los tickets deben clasificarse por celeridad (1, 2, 3) con tiempos definidos | Define lógica de prioridad y SLA | Minuta Usuarios PSA |
| RN-03 | Proyectos e Implementaciones deben seguir la misma metodología | Unifica estructura de datos y procesos | Minuta Usuarios PSA |
| RN-04 | El sistema debe permitir medir ROI de campañas | Requiere registro de costos e ingresos | Minuta Usuarios PSA |
| RN-05 | El sistema se valida primero internamente antes de comercialización | Define estrategia de despliegue | Minuta CEO PSA |
| RN-06 | Las horas cargadas deben validarse contra el calendario laboral | Controla que no se excedan horas diarias | Minuta 2026-04-30 |
| RN-07 | Todo lead debe estar asociado a una campaña y un canal de origen | Garantiza trazabilidad comercial y métricas confiables | Minuta Marketing y Ventas |
| RN-08 | Una oportunidad puede incluir múltiples productos o servicios | Afecta estructura comercial y cálculo de ingresos | Minuta Marketing y Ventas |
| RN-09 | Una oportunidad solo puede pasar a Implementaciones cuando se marca como ganada | Controla transición entre Ventas e Implementaciones | Minuta Marketing y Ventas |
| RN-10 | El ROI de campañas no debe contemplar horas internas del equipo de marketing | Simplifica el cálculo inicial de rentabilidad | Minuta Marketing y Ventas |
| RN-11 | El sistema debe conservar el historial de productos considerados y vendidos en cada oportunidad comercial | Mantiene trazabilidad completa del proceso comercial | Minuta 2026-05-07 (Marketing y Ventas) — Confirmaciones |
| RN-12 | El MVP no incluirá controles avanzados de seguridad ni restricciones de acceso por roles | Permite priorizar funcionalidades de negocio sobre seguridad en la versión inicial | Minuta 2026-05-07 (Marketing y Ventas) — Hallazgos |
| RN-13 | Una oportunidad puede pasar por la etapa de Negociación antes de resolverse como Ganada o Perdida | Modela el flujo comercial real de cierre de ventas | Minuta 2026-05-07 (Marketing y Ventas) — Hallazgos |

## Dudas abiertas

| ID | Duda | Impacto | Próxima acción |
| --- | --- | --- | --- |
| D-01 | ¿Cómo impactará la IA en el sistema y qué funcionalidades incorporar? | Puede cambiar arquitectura y alcance futuro | Definir estrategia con CEO |
| D-02 | ¿Qué módulo se desarrollará primero en detalle (priorización fina)? | Afecta planificación del proyecto | Definir con stakeholders |
| D-03 | ¿Qué métricas exactas se usarán para calcular ROI? | Impacta diseño de datos de marketing | Reunión con Marketing |
| D-04 | ¿Cómo adaptar el sistema a normativas de distintos países? | Impacta escalabilidad internacional | Análisis legal |
| D-05 | ¿Quiénes serán los usuarios finales específicos por módulo? | Impacta diseño de UX y permisos | Entrevistas adicionales |
| D-06 | ¿Cómo se modelarán las etapas detalladas del pipeline comercial y negociación? | Impacta flujo comercial y estados de oportunidades | Refinar con Ventas |
| D-07 | ¿Qué nivel de integración existirá entre módulos en el MVP inicial? | Impacta arquitectura y alcance | Definir con stakeholders |
| D-08 | ¿Qué métricas comerciales y financieras serán obligatorias en dashboards ejecutivos? | Impacta reporting y diseño analítico | Reunión con Dirección |