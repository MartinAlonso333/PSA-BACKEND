# R1.1 - Organigrama

## Sistema analizado

- Praxis Systems Argentina (PSA): compañía de desarrollo y venta de software empresarial con base en Buenos Aires y clientes en Sudamérica de habla hispana.
- Plataforma Cloud de productos ERP, CRM y BI.
- El sistema en desarrollo busca integrar la gestión de proyectos, implementaciones, soporte, marketing, ventas, incidentes, horas y métricas comerciales.

## Alcance organizacional

- La compañía está dirigida por Juan Zeo, socio fundador y accionista mayoritario.
- La estructura organizacional se divide en cuatro gerencias principales:
  - Operaciones
  - Marketing
  - Ventas
  - Administración y Finanzas
- El sistema impactará transversalmente sobre todas las áreas operativas y comerciales.
- Product Owner, Project Manager y Jefatura de Proyecto participan como roles transversales de coordinación funcional y metodológica.

## Roles y áreas relevantes

| ID | Área o rol | Relación con el módulo | Evidencia |
| --- | --- | --- | --- |
| O-01 | CEO / Gerente General (Juan Zeo) | Dirige PSA, define visión estratégica y criterios de éxito del producto (ROI, captación de clientes, diferenciación frente a competencia e IA). | Minuta CEO PSA |
| O-02 | Gerente de Operaciones (Fernando Zoluzzia) | Responsable del área de Operaciones y de los módulos vinculados a proyectos, implementaciones, soporte e infraestructura. | Minuta Usuarios PSA |
| O-03 | Gerente de Finanzas (Ricardo Ratio) | Responsable de Administración y Finanzas, incluyendo facturación, costos y planeamiento financiero. | Caso PSA y entrevistas |
| O-04 | Gerente de Ventas (Juan Anvizzio) | Responsable del proceso comercial, oportunidades, pipeline y conversión de leads. | Minuta Marketing y Ventas |
| O-05 | Gerente de Marketing (José Mercado) | Responsable de campañas, generación de leads, posicionamiento y métricas de ROI. | Minuta Usuarios PSA y Marketing y Ventas |
| O-06 | Consultora especializada (Belén Sappe) | Participa en tareas de consultoría funcional y análisis de necesidades de clientes. | Caso PSA |
| O-07 | Project Manager (Leonardo Felici) | Coordina proyectos y seguimiento operativo transversal entre áreas. | Minuta PM-PO-JP |
| O-08 | Product Owner (Tomás Bruneleschi) | Responsable funcional del producto y priorización de requerimientos. | Minuta PM-PO-JP |
| O-09 | Jefe de Proyecto (Maximiliano) | Responsable operativo de coordinación y seguimiento de proyectos. | Minuta PM-PO-JP |
| O-10 | Gerencia de Operaciones | Responsable del desarrollo de productos, implementaciones, soporte, infraestructura y seguridad. | Minuta Usuarios PSA |
| O-11 | Gerencia de Marketing | Responsable de campañas, generación de leads, posicionamiento e interoperabilidad comercial. | Minuta Usuarios PSA |
| O-12 | Gerencia de Ventas | Responsable de leads, oportunidades, negociaciones, cierre comercial y relación con implementación. | Minuta Marketing y Ventas |
| O-13 | Gerencia de Administración y Finanzas | Responsable de facturación, compras, impuestos, tesorería, sueldos y métricas financieras. | Caso PSA y PM-PO-JP |
| O-14 | Área de Desarrollo de Productos | Responsable de la evolución de productos ERP, CRM y BI y de proyectos de desarrollo. | Minuta Usuarios PSA |
| O-15 | Área de Seguridad | Responsable de políticas de seguridad y prácticas de desarrollo seguro. | Caso PSA |
| O-16 | Área de Implementaciones | Responsable de puesta en marcha de productos y proyectos de implementación por cliente. | Minuta Usuarios PSA |
| O-17 | Área de Operaciones y Soporte | Responsable de tickets, incidentes, SLA, monitoreo y atención al cliente. | Minuta Usuarios PSA y PM-PO-JP |
| O-18 | Área de Infraestructura y Plataformas | Responsable de la operación cloud e infraestructura tecnológica. | Minuta Usuarios PSA |
| O-19 | Área de Marketing Comercial | Gestiona campañas, leads, ROI y efectividad comercial. | Minuta Marketing y Ventas |
| O-20 | Área Comercial / Ventas | Gestiona oportunidades, negociaciones, pipeline y cierre de ventas. | Minuta Marketing y Ventas |
| O-21 | Equipo de Desarrollo / Squad 1 | Responsable del relevamiento, análisis y desarrollo inicial del sistema. | Todas las minutas |

## Diagrama

```mermaid
flowchart TB

    CEO["CEO / Gerente General<br>(Juan Zeo)"]

    CEO --> GO["Gerencia de Operaciones<br>(Fernando Zoluzzia)"]
    CEO --> GF["Gerencia de Administración y Finanzas<br>(Ricardo Ratio)"]
    CEO --> GV["Gerencia de Ventas<br>(Juan Anvizzio)"]
    CEO --> GM["Gerencia de Marketing<br>(José Mercado)"]

    %% Operaciones
    GO --> DEV["Área de Desarrollo de Productos"]
    GO --> IMP["Área de Implementaciones"]
    GO --> SOP["Área de Operaciones y Soporte"]
    GO --> INF["Área de Infraestructura y Plataformas"]
    GO --> SEG["Área de Seguridad"]

    %% Marketing y Ventas
    GM --> MKT["Área de Marketing Comercial"]
    GV --> VEN["Área Comercial / Ventas"]

    %% Roles transversales
    CEO -. coordinación transversal .-> PM["Project Manager<br>(Leonardo Felici)"]
    CEO -. coordinación funcional .-> PO["Product Owner<br>(Tomás Bruneleschi)"]
    CEO -. seguimiento operativo .-> JP["Jefe de Proyecto<br>(Maximiliano)"]

    %% Relación entre módulos
    MKT -->|"Genera leads"| VEN
    VEN -->|"Oportunidad ganada"| IMP
    SOP -->|"Derivación de tickets"| DEV
    SOP -->|"Derivación de tickets"| IMP

    %% Finanzas
    GF --> FIN["Facturación y Planeamiento Financiero"]

    %% Squad
    PM -. coordinación .-> SQ["Squad 1<br>Equipo de Desarrollo"]
    PO -. priorización .-> SQ
    JP -. seguimiento .-> SQ

## Observaciones

- Product Owner, Project Manager y Jefatura de Proyecto son roles transversales que coordinan múltiples áreas y participan activamente en la definición funcional del sistema.

- El sistema propuesto tendrá un enfoque modular:
  - Proyectos
  - Implementaciones
  - Soporte e Incidentes
  - Marketing
  - Ventas / CRM
  - Horas y Rentabilidad
  - Infraestructura

- Marketing y Ventas tienen una fuerte integración mediante el flujo:
  - Campaña → Lead → Oportunidad → Negociación → Cliente → Implementación.

- Soporte funcionará como punto centralizador de interacción con clientes y derivará tickets hacia Desarrollo e Implementaciones.

- La primera versión priorizará:
  - Proyectos
  - Implementaciones
  - Soporte
  - Marketing y Ventas

- Infraestructura tendrá menor prioridad en el MVP.

- Se identificó necesidad de:
  - Estandarización de procesos
  - Gestión de SLA
  - Métricas de ROI
  - Control de horas y costos
  - Integración entre áreas
  - Mejora en comunicación interna

- Squad 1 actúa como equipo responsable del relevamiento, análisis y construcción inicial del sistema.

