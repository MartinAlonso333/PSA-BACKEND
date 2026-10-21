# V1 - Visión del Módulo (MVP)

## Nombre del módulo

- Módulo: Marketing.

## Problema u oportunidad

- Falta de integración entre Marketing y otras áreas.
- Imposibilidad de trazar correctamente el origen de los leads.
- Ausencia de métricas clave como ROI por campaña.
- Falta de visibilidad sobre el rendimiento de campañas.
- Limitaciones para tomar decisiones basadas en datos.
- Oportunidad de mejorar la captación de clientes.

## Resumen ejecutivo

- El módulo de Marketing tiene como objetivo centralizar la gestión de campañas y la generación de leads, permitiendo trazar su origen, medir su rendimiento y mejorar la toma de decisiones basada en datos.
- A través de este módulo, la empresa podrá vincular campañas con resultados concretos, facilitando el análisis de efectividad y el seguimiento de las acciones de marketing.
- El módulo permitirá administrar campañas, registrar leads, identificar canales de origen y realizar un seguimiento básico del estado de los leads.

## Alcance del MVP

- Registro y gestión de campañas de marketing.
- Registro de leads.
- Asociación de leads a campañas.
- Identificación del origen y canal de cada lead.
- Registro de empresa (entidad con Nombre, RazonSocial, CUIT, Direccion) y datos de contacto del lead.
- Seguimiento básico del estado del lead.
- Estados básicos para campañas.
- Métricas iniciales de rendimiento:
  - Leads por campaña.
  - Conversión de leads.
  - ROI básico.
- Asociación de campañas con resultados comerciales para medir efectividad.

## Fuera de alcance

- Automatización avanzada de campañas (email marketing, campañas inteligentes).
- Integración completa con herramientas externas (ej: redes sociales, plataformas de ads).
- Modelos predictivos o uso de IA.
- Segmentación avanzada de clientes.
- Comparativas automáticas entre campañas.
- Gestión avanzada de permisos y seguridad.
- Alertas automáticas y seguimiento automatizado.
- Gestión detallada de presupuesto operativo interno.

## Funcionalidades principales

| ID | Funcionalidad | Valor |
| --- | --- | --- |
| MVP-01 | Registrar campañas de marketing | Permite organizar y centralizar acciones de marketing |
| MVP-02 | Gestionar estados de campañas | Facilita el seguimiento del ciclo de vida de campañas |
| MVP-03 | Registrar leads | Captura oportunidades generadas |
| MVP-04 | Asociar leads a campañas | Permite trazabilidad del origen |
| MVP-05 | Registrar canal y origen del lead | Mejora análisis de adquisición |
| MVP-06 | Registrar empresa y contacto del lead | Centraliza información comercial |
| MVP-07 | Seguimiento básico de lead | Permite visualizar estado de avance |
| MVP-08 | Métrica de leads por campaña | Primer indicador de rendimiento |
| MVP-09 | Métrica básica de conversión | Permite evaluar efectividad |
| MVP-10 | Consulta básica de ROI | Relaciona campañas con resultados obtenidos |

## Estados principales

### Campañas

- Planificada.
- Activa.
- Finalizada.

### Leads

- Nuevo.
- Contactado.
- Calificado.
- Convertido en oportunidad.
- Perdido

## Interacciones con otros módulos

| Módulo o actor | Tipo de interacción | Qué intercambia |
| --- | --- | --- |
| Producto / Estrategia | Entrada | Información de productos y lanzamientos |
| Ventas | Salida | Leads convertidos en oportunidades; Marketing transfiere el lead al alcanzar el estado "Convertido en oportunidad"; Ventas gestiona el pipeline comercial a partir de ese punto |
| Implementaciones | Referencia | Sin interacción directa desde Marketing; Implementaciones se activa cuando Ventas cierra una oportunidad como ganada |
| CRM externo (futuro) | Integración | Leads y campañas |

## Reglas de negocio relevantes

- Todo lead debe estar asociado a una campaña de origen.
- Todo lead debe registrar un canal de origen.
- Una campaña puede generar múltiples leads.
- El ROI se calculará utilizando costos de campaña e ingresos asociados a resultados obtenidos.
- El sistema debe conservar trazabilidad entre campañas y leads.

## Reportes esperados en el MVP

- Leads por campaña.
- Conversión de leads.
- ROI básico por campaña.
- Efectividad de campañas.

## Criterios de éxito del MVP

- Se pueden registrar campañas y asociarles leads correctamente.
- Se puede identificar el origen y canal de cada lead.
- Existe trazabilidad entre campañas y leads.
- Se obtiene visibilidad básica del rendimiento de campañas.
- Se pueden consultar métricas iniciales de conversión y ROI.
- Mejora en la organización y trazabilidad del proceso de captación de clientes.
- Disponibilidad de datos para futuras automatizaciones e integraciones.