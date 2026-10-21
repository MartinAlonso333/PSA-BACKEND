# R4.1 - Historias de Usuario — Sistema PSA

## Backlog

| ID | Historia | Evidencia principal | Épica opcional |
| --- | --- | --- | --- |
| HU-01 | Como agente de soporte, quiero registrar un ticket desde cualquier canal de comunicación, para asegurar trazabilidad centralizada de cada requerimiento del cliente. | RF-01, RN-01, Historia 2 | Soporte          |
| HU-02 | Como agente de soporte, quiero clasificar un ticket con una celeridad al momento de su creación, para garantizar que cada caso tenga un SLA definido.     | RF-09, RN-02, Historia 4 | Soporte          |
| HU-03 | Como agente de soporte, quiero escalar formalmente un ticket a Desarrollo o Implementaciones, para resolver casos que superan el nivel inicial de atención.           | RF-02, Historia 3        | Soporte          |
| HU-04 | Como líder de proyecto, quiero iniciar un proyecto definiendo alcance, roles y riesgos, para asegurar que todo proyecto comience con estándares unificados.           | RF-03, RF-04, Historia 1 | Proyectos        |
| HU-05 | Como líder de proyecto, quiero administrar los riesgos durante el ciclo de vida del proyecto, para detectar y mitigar problemas de forma temprana.            | RF-10, Historia 1        | Proyectos        |
| HU-06 | Como responsable de marketing, quiero registrar una campaña con sus indicadores clave, para medir la efectividad y el retorno de las acciones comerciales realizadas          | RF-05, RN-04, Historia 5 | Marketing        |
| HU-07 | Como ejecutivo de ventas, quiero avanzar un lead a través de las etapas del pipeline de forma secuencial, para asegurar la integridad del proceso comercial.    | RF-06, Historia 6        | Marketing/Ventas |
| HU-08 | Como responsable de marketing, quiero calcular el ROI de una campaña vinculando costos e ingresos generados, para tomar decisiones basadas en datos.                 | RF-07, RN-04, Historia 8 | Marketing        |
| HU-09 | Como miembro de dirección, quiero visualizar métricas consolidadas de todos los módulos sin exportaciones manuales, para analizar el desempeño global del negocio.   | RF-08, Historia 7        | Integración      |
| HU-10 | Como gestor de proyecto, quiero cargar horas diarias por tarea, para obtener métricas de costos y rentabilidad. | RF-11, RN-06, Historia 10 | Proyectos |
| HU-11 | Como analista, quiero recibir reportes de ingresos vs costos por producto, para evaluar la rentabilidad de cada proyecto. | RF-12, RN-06, Historia 11 | Marketing |
| HU-12 | Como ejecutivo comercial, quiero registrar el estado detallado de cada lead, para realizar seguimiento comercial preciso. | RF-13, Historia 5 | Marketing/Ventas |
| HU-13 | Como responsable de marketing, quiero priorizar productos específicos dentro de las campañas, para responder a estrategias comerciales y competencia del mercado. | RF-14, Historia 5 | Marketing |
| HU-14 | Como responsable de marketing, quiero visualizar métricas de efectividad de campañas y conversión de leads, para evaluar el rendimiento comercial de las acciones de marketing. | RF-15, RN-04, Historia 8 | Marketing |
| HU-15 | Como ejecutivo de ventas, quiero asociar múltiples productos o servicios a una oportunidad comercial, para reflejar correctamente propuestas complejas. | RF-16, Historia 6 | Ventas |
| HU-16 | Como responsable de implementaciones, quiero recibir oportunidades ganadas desde Ventas, para iniciar proyectos de implementación con trazabilidad comercial. | RF-17, Historia 6 | Implementaciones |
| HU-17 | Como supervisor de soporte, quiero recibir alertas sobre tickets cercanos a incumplir SLA, para reducir escalaciones y multas. | RF-18, RN-02, Historia 4 | Soporte |
| HU-18 | Como colaborador, quiero registrar licencias y ausencias dentro del sistema, para reflejar correctamente disponibilidad y costos operativos. | RF-19, Historia 10 | Proyectos |
| HU-19 | Como colaborador de PSA, quiero centralizar la comunicación y derivación entre áreas dentro del sistema, para reducir pérdida de información y mejorar trazabilidad. | RF-20, Historia 3 | Integración |
| HU-20 | Como miembro de dirección, quiero mantener trazabilidad completa entre campañas, ventas, proyectos, horas y facturación, para analizar rentabilidad y desempeño operativo integral. | RF-21, Historia 7 | Integración |
| HU-21 | Como ejecutivo de ventas, quiero actualizar el estado de un lead, para asegurar un seguimiento comercial preciso y una correcta conversión a oportunidades. | RF-23, RF-06, RF-13, Historia 3 | Marketing/Ventas |
| HU-22 | Como responsable de marketing, quiero registrar el canal de origen de cada lead, para analizar qué canales generan mejores resultados. | RF-24, RN-07, Historia 2 | Marketing |
| HU-23 | Como responsable de implementaciones, quiero recibir los datos de una oportunidad ganada, para iniciar el proyecto de implementación con toda la información necesaria. | RF-17, RN-09, Historia 4 | Implementaciones |
| HU-24 | Como responsable de marketing, quiero asociar una campaña a los leads generados, para medir el impacto comercial de cada acción.                  | RF-05, RN-04, Historia 5 | Marketing        |
| HU-25 | Como responsable de marketing, quiero consultar el catálogo de productos y canales disponibles, para conocer la oferta y los medios de captación.  | RF-05, RF-24             | Marketing        |
| HU-26 | Como responsable de marketing, quiero consultar y registrar empresas en el sistema, para mantener actualizados los datos corporativos de los leads. | RF-05, RF-24             | Marketing        |

## Historias priorizadas

| ID | Motivo de prioridad | Feature esperado |
| --- | --- | --- |
| HU-01 | Núcleo del módulo de soporte; sin tickets centralizados el resto del módulo no tiene base. | `src/test/resources/features/soporte/HU-01.feature`|
| HU-02 | Depende de HU-01; define SLA obligatorio en la creación del ticket (regla de negocio crítica RN-02).| `src/test/resources/features/soporte/HU-02.feature` |
| HU-03 | Habilita la coordinación entre áreas; desbloquea flujos de escalación trazables. | `src/test/resources/features/soporte/HU-03.feature` |
| HU-04 | Base del módulo de proyectos; estandariza la metodología compartida con implementaciones. | `src/test/resources/features/proyectos/HU-04.feature` |
| HU-06 | Habilita trazabilidad campaña → lead, prerequisito para HU-07 y HU-08. | `src/test/resources/features/HU-06.feature` |
| HU-07 | Modela el pipeline comercial; prerequisito para medir resultados de ventas. | `src/test/resources/features/HU-07.feature` |
| HU-10 | Carga horaria diaria por tarea – métricas de costos y rentabilidad. | `src/test/resources/features/proyectos/HU-10.feature` |
| HU-11 | Reportes de ingresos vs costos por producto – evaluación de rentabilidad. | `src/test/resources/features/finanzas/HU-11.feature` |
| HU-21 | Modela los estados del lead (Nuevo → Contactado → Calificado → Convertido/Perdido) como eje del flujo comercial Marketing→Ventas. | `src/test/resources/features/ventas/HU-21.feature` |
| HU-22 | Obliga el registro del canal de origen del lead para análisis de efectividad de campañas. | `src/test/resources/features/HU-22.feature` |
| HU-24 | Habilita trazabilidad campaña → lead, prerequisito para HU-07 y HU-08. | `src/test/resources/features/HU-24.feature` |
| HU-25 | Consulta de catálogos; necesario para asociar productos y canales a campañas y leads. | `src/test/resources/features/HU-25.feature` |
| HU-26 | Gestión de empresas; necesario para mantener datos corporativos actualizados de los leads. | `src/test/resources/features/HU-26.feature` |
| HU-08 | Permite calcular el ROI vinculando costos e ingresos; prerequisito para evaluar rentabilidad de campañas. La tribu lo requiere como métrica del MVP. | `src/test/resources/features/HU-08.feature` |
| HU-14 | Permite visualizar métricas de efectividad y conversión de leads; prerequisito para evaluar rendimiento comercial. La tribu lo requiere como métrica del MVP. | `src/test/resources/features/HU-14.feature` |
## Criterios de calidad

- Cada historia priorizada debe tener exactamente un `.feature` con correspondencia 1:1.
- Todos los `.feature` deben declarar `# language: es` como primera línea.
- Ningún `.feature` debe mezclar escenarios de más de una historia de usuario.
- Toda historia referencia al menos un requisito funcional (RF-XX) o regla de negocio (RN-XX) como trazabilidad.
- Las historias siguen el formato estándar: `Como <actor>, quiero <acción>, para <beneficio>`.
- Las historias de marketing fueron reestructuradas para priorizar el objetivo de negocio antes de la funcionalidad técnica, siguiendo recomendaciones de BDD orientadas al valor. En lugar del formato tradicional “Como [rol], quiero [funcionalidad], para [valor]”, se utilizó una redacción centrada primero en el resultado esperado del negocio (“Para [valor]”), favoreciendo una mejor alineación entre las necesidades comerciales y los escenarios definidos.