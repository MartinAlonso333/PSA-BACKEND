# R4.2 - Matriz de Trazabilidad

## Criterio de trazabilidad

Se vincula cada Historia de Usuario con las minutas donde se identificaron necesidades, decisiones o requerimientos que la sustentan. La vinculación se realiza mediante coincidencia semántica entre:

- **Acciones descritas en la HU** y **necesidades/hallazgos documentados en las minutas**
- **Requisitos funcionales (RF-XX) y reglas de negocio (RN-XX)** referenciados en cada HU, rastreados hasta su fuente en las minutas
- **Vocabulario del dominio** que aparece tanto en las minutas como en las historias (ej: "celeridad", "pipeline", "lead", "escalación", "ROI", "campaña")

Una minuta se considera evidencia cuando menciona directa o indirectamente la funcionalidad que modela la HU, ya sea como:
- Problema a resolver
- Decisión tomada
- Proceso estandarizado
- Requerimiento explícito

## Matriz HDU vs minutas

| HU | Evidencia en minutas | Requisitos asociados | Feature | Observaciones |
| --- | --- | --- | --- | --- |
| HU-01 | Minuta Usuarios PSA 2026-04-23 (múltiples canales sin trazabilidad, necesidad de ticket único) | RF-01, RN-01 | `HU-01.feature` | Se fundamenta en la centralización obligatoria de tickets. |
| HU-02 | Minuta Usuarios PSA 2026-04-23 (definición de celeridades 1, 2 y 3 con SLA asociados) | RF-09, RN-02 | `HU-02.feature` | La lógica de SLA surge directamente de la minuta. |
| HU-03 | Minuta Usuarios PSA 2026-04-23 (derivación de tickets hacia Desarrollo e Implementaciones) | RF-02 | `HU-03.feature` | Formaliza el flujo de escalación entre áreas. |
| HU-04 | Minuta Usuarios PSA 2026-04-23 (falta de estandarización de proyectos y riesgos) | RF-03, RF-04 | `HU-04.feature` | Base del módulo de proyectos e implementaciones. |
| HU-05 | Minuta Usuarios PSA 2026-04-23 (seguimiento de riesgos y reportes) | RF-10 | `HU-05.feature` | Necesario para prevenir atrasos y problemas operativos. |
| HU-06 | Minuta Usuarios PSA 2026-04-23 y Minuta Marketing y Ventas 2026-05-07 (campañas generan leads y deben asociarse) | RF-05, RN-04 | `src/test/resources/features/HU-06.feature` | Sustenta la trazabilidad campaña → lead. |
| HU-07 | Minuta Usuarios PSA 2026-04-23 y Marketing/Ventas 2026-05-07 (pipeline comercial y etapas de seguimiento) | RF-06 | `src/test/resources/features/HU-07.feature` | Modela el flujo comercial completo. |
| HU-08 | Minuta Usuarios PSA 2026-04-23 y Marketing/Ventas 2026-05-07 (ROI basado en costos e ingresos asociados) | RF-07, RN-04 | `src/test/resources/features/HU-08.feature` | El cálculo de ROI requiere trazabilidad comercial. |
| HU-09 | Minuta CEO PSA y PM-PO-JP (necesidad de métricas globales y visibilidad operativa) | RF-08 | `HU-09.feature` | Integra información transversal de todos los módulos. |
| HU-10 | Minuta PM-PO-JP 2026-04-30 (carga diaria de horas y métricas de costos) | RF-11, RN-06 | `HU-10.feature` | Base para rentabilidad y costos reales. |
| HU-11 | Minuta PM-PO-JP 2026-04-30 (ingresos vs costos por producto/proyecto) | RF-12, RN-06 | `HU-11.feature` | Necesario para análisis financiero y estratégico. |
| HU-12 | Minuta Usuarios PSA y Marketing/Ventas (seguimiento granular de prospects y pipeline comercial) | RF-13 | `HU-12.feature` | Permite trazabilidad detallada del estado comercial. |
| HU-13 | Minuta Usuarios PSA (priorización de productos en campañas frente a competencia) | RF-14 | `HU-13.feature` | Relacionada con posicionamiento estratégico y marketing. |
| HU-14 | Minuta Marketing y Ventas (reportes de efectividad, conversión y campañas) | RF-15, RN-04 | `src/test/resources/features/HU-14.feature` | Extiende las métricas de marketing y ventas. |
| HU-15 | Minuta Marketing y Ventas (una oportunidad puede incluir múltiples productos o servicios) | RF-16 | `HU-15.feature` | Modela oportunidades comerciales complejas. |
| HU-16 | Minuta Marketing y Ventas (transferencia de oportunidad ganada a Implementaciones) | RF-17 | `HU-16.feature` | Asegura continuidad entre ventas e implementación. |
| HU-17 | Minuta Usuarios PSA y PM-PO-JP (alertas por incumplimiento de SLA) | RF-18, RN-02 | `HU-17.feature` | Reduce multas y escalaciones críticas. |
| HU-18 | Minuta PM-PO-JP (registro de licencias y ausencias) | RF-19 | `HU-18.feature` | Impacta disponibilidad operativa y costos. |
| HU-19 | Minuta Usuarios PSA (problemas de comunicación entre áreas y derivaciones informales) | RF-20 | `HU-19.feature` | Busca centralizar y formalizar la comunicación interna. |
| HU-20 | Minuta CEO PSA, PM-PO-JP y Marketing/Ventas (necesidad de trazabilidad integral y métricas globales) | RF-21 | `HU-20.feature` | Integra campañas, ventas, proyectos, horas y facturación. |
| HU-21 | Minuta 2026-05-07 (Marketing y Ventas) — Hallazgos: estados del lead (Nuevo, Contactado, Calificado, Convertido en oportunidad, Perdido) | RF-23, RF-06, RF-13 | `src/test/resources/features/ventas/HU-21.feature` | Modela los estados del lead como eje del flujo comercial Marketing → Ventas. |
| HU-22 | Minuta 2026-05-07 (Marketing y Ventas) — Hallazgos: canal de origen obligatorio por lead | RF-24, RN-07 | `src/test/resources/features/HU-22.feature` | Obliga a registrar el canal de origen del lead para análisis de efectividad de campañas. |
| HU-23 | Minuta 2026-05-07 (Marketing y Ventas) — Confirmaciones: "El paso a implementación ocurre únicamente cuando una oportunidad se marca como ganada" | RF-17, RN-09 | `HU-23.feature` | Cierra el flujo comercial transfiriendo datos a Implementaciones. |
| HU-24 | Minuta Usuarios PSA 2026-04-23 y Minuta Marketing y Ventas 2026-05-07 (asociación entre campañas y leads generados para medir impacto comercial) | RF-05, RN-04 | `src/test/resources/features/HU-24.feature` | Habilita la trazabilidad campaña → lead, prerequisito para HU-07 y HU-08. |
| HU-25 | Minuta Marketing y Ventas 2026-05-07 (productos y canales como parte integral de la gestión de campañas y leads) | RF-05, RF-24 | `src/test/resources/features/HU-25.feature` | Catálogos de productos y canales; soporte para asociación en campañas y leads. |
| HU-26 | Minuta Usuarios PSA 2026-04-23 y Minuta Marketing y Ventas 2026-05-07 (necesidad de datos corporativos de empresas vinculadas a leads) | RF-05, RF-24 | `src/test/resources/features/HU-26.feature` | Gestión de empresas como entidad JPA separada; soporte para deduplicación por CUIT. |

## Trazabilidad de implementación — Módulo Marketing

| HU / RF | Clase del modelo | Atributos relevantes | Endpoint REST | Clase de servicio | Steps class | Feature file | Observaciones |
| ------- | ---------------- | -------------------- | ------------- | ----------------- | ----------- | ------------ | ------------- |
| HU-06 | `Campaña` | `Nombre`, `FechaInicio`, `FechaFin`, `Presupuesto`, `Costo`, **`Roi`**, `Estado`, `Productos`, `Canales` | `POST /api/campanias`, `GET /api/campanias/{id}/metricas`, `PUT /api/campanias/{id}`, `DELETE /api/campanias/{id}` | `CampaniaService` | `HU06Steps` | `src/test/resources/features/HU-06.feature` | Endpoints directos de la HU (incluye PUT y DELETE con escenarios BDD) |
| | | | `GET /api/campanias`, `GET /api/campanias/{id}`, `PATCH /api/campanias/{id}/estado` | `CampaniaService` | — | — | Endpoints de soporte (listar, obtener, cambio de estado) |
| HU-07 | `Lead` | `Nombre`, `Empresa`, `Estado` | `PATCH /api/leads/{id}/pipeline`, `PATCH /api/leads/{id}/cierre`, `DELETE /api/leads/{id}`, `GET /api/leads`, `GET /api/leads/{id}` | `LeadService` | `HU07Steps` | `src/test/resources/features/HU-07.feature` | Endpoints directos de la HU (incluye listado y detalle) |
| | | | `PUT /api/leads/{id}` | `LeadService` | `HU22Steps` | `src/test/resources/features/HU-22.feature` | Ahora con escenario BDD en HU-22 |
| HU-22 | `Lead`, `Canal` | `CanalOrigen`, `CanalAnterior`, `CampaniaOrigen` | `PATCH /api/leads/{id}/canal-origen`, `GET /api/leads/reporte/por-canal`, `PUT /api/leads/{id}` | `LeadService` | `HU22Steps` | `src/test/resources/features/HU-22.feature` | Incluye actualización de datos del lead |
| HU-24 | `Lead`, `Campaña`, `Contacto`, `Producto`, `Canal` | — | `POST /api/leads` | `LeadService` | `HU24Steps` | `src/test/resources/features/HU-24.feature` | |
| HU-25 | `Producto`, `Canal` | `Nombre`, `Codigo` / `Nombre`, `Tipo` | `GET /api/productos`, `GET /api/productos/{id}`, `GET /api/canales`, `GET /api/canales/{id}` | `ProductoService`, `CanalService` | `HU25Steps` | `src/test/resources/features/HU-25.feature` | Catálogos de productos y canales |
| HU-26 | `Empresa` | `Nombre`, `RazonSocial`, `CUIT`, `Direccion` | `GET /api/empresas`, `GET /api/empresas/{id}`, `POST /api/empresas` | `EmpresaService` | `HU26Steps` | `src/test/resources/features/HU-26.feature` | CRUD de empresas con deduplicación por CUIT |
| HU-08 | `Campaña` | `Roi`, `Costo`, `leadsGenerados`, `leadsConvertidos`, `conversion`, `montoGanadoTotal` | `GET /api/campanias/{id}/metricas` | `CampaniaService` | `MetricasSteps` | `src/test/resources/features/HU-08.feature` | Cálculo de ROI = (montoGanadoTotal - costo) / costo; montoGanadoTotal consulta a Ventas |
| HU-14 | `Campaña`, `Lead` | `leadsGenerados`, `leadsConvertidos`, `conversion` | `GET /api/campanias/{id}/metricas` | `CampaniaService` | `MetricasSteps` | `src/test/resources/features/HU-14.feature` | Métricas de efectividad y conversión de leads |
| RF-22 | `Campaña` | `Estado` (transiciones `Planificada → Activa → Finalizada`) | `PATCH /api/campanias/{id}/estado` | `CampaniaService` | — | — | Sin HU priorizada en esta iteración; respaldado únicamente por RF-22 |

### Notas sobre atributos y requisitos del modelo en esta iteración

- `Campaña`: se incorporó **`Roi`** como atributo simple (ya no se modela como derivado).
- `Ventas::Oportunidad`: nuevos atributos **`MontoEsperado`** (antes `TipoServicio`). Su implementación corresponde al módulo Ventas.
- `Ventas::Cliente`: se incorpora con **`HU-11`** (reportes de ingresos vs costos por producto). La tabla `cliente` incluye `estado` y `monto_ganado` para mantener trazabilidad desde Marketing.
- `RF-22` — **Gestión de estados de campaña**: el endpoint `PATCH /api/campanias/{id}/estado` expone las transiciones `Planificada → Activa → Finalizada`. La lógica de dominio está implementada, pero no hay una HU priorizada que lo canalice. RF-22 lo respalda a nivel de requisito funcional.
- `Campaña`: se incorporaron **`Productos`** y **`Canales`** como atributos del modelo. Se agregaron endpoints **`DELETE /api/campanias/{id}`** (con validación de leads asociados) y **`DELETE /api/leads/{id}`** (con restricción de etapa NUEVO/CONTACTADO/CALIFICADO).

## Cobertura pendiente

Las siguientes preguntas surgen del análisis de inconsistencias y faltantes de cobertura detectados. Esta es una segunda iteración del proyecto: aún no se conocen todos los detalles del dominio ni de la implementación. Las preguntas buscan comprender mejor el problema antes de especificar la solución.

## Sobre integración y comunicación entre áreas

1. ¿Cómo se comparte actualmente la información entre Marketing, Ventas, Soporte y Operaciones?
2. ¿Qué problemas aparecen con más frecuencia por falta de información compartida?
3. ¿Cómo se notifican hoy las escalaciones o problemas críticos entre áreas?
4. ¿Qué tipo de información necesita cada área y no recibe a tiempo?

## Sobre proyectos, riesgos y operación

5. ¿Qué riesgos aparecen con mayor frecuencia en proyectos e implementaciones?
6. ¿Cómo se realiza actualmente el seguimiento de riesgos?
7. ¿Existe trazabilidad histórica de incidentes, atrasos o desvíos?
8. ¿Cómo se gestionan hoy las dependencias entre proyectos y soporte?

## Sobre marketing y ventas

9. ¿Cómo se mide actualmente la efectividad de una campaña?
10. ¿Qué métricas comerciales consideran más importantes para dirección?
11. ¿Cómo identifican actualmente el origen de un lead?
12. ¿Qué información comercial se pierde hoy entre Marketing y Ventas?
13. ¿Cómo se decide qué producto priorizar en una campaña?
14. ¿Qué información necesita Ventas para avanzar un lead correctamente?

## Sobre oportunidades y pipeline comercial

15. ¿Qué etapas comerciales generan más pérdida de oportunidades?
16. ¿Cuáles son las causas más frecuentes de oportunidades perdidas?
17. ¿Qué condiciones deben cumplirse para considerar un lead “calificado”?
18. ¿Cómo se realiza actualmente el traspaso hacia Implementaciones?

## Sobre costos, horas y rentabilidad

19. ¿Cómo se validan actualmente las horas cargadas?
20. ¿Qué costos reales hoy no pueden medirse correctamente?
21. ¿Qué reportes financieros o comerciales son prioritarios para dirección?
22. ¿Cómo se calcula actualmente la rentabilidad de un proyecto o producto?

## Sobre crecimiento futuro del sistema

23. ¿Qué integraciones externas serán prioritarias luego del MVP?
24. ¿Qué funcionalidades relacionadas con IA podrían incorporarse a futuro?
25. ¿Qué restricciones regulatorias podrían afectar la expansión internacional del sistema?