# Entrega 03 - Arquitectura, interfaces y base de datos

## Entrega y alcance

- Consolidación del módulo de Marketing del sistema PSA.
- Definición de arquitectura inicial, contratos API y persistencia relacional.
- Modelado completo del flujo comercial desde campañas hasta ventas.
- Integración conceptual entre Marketing y Ventas mediante oportunidades comerciales.
- Definición de trazabilidad entre campañas, leads, oportunidades y ventas.
- Formalización del pipeline comercial de leads.
- Incorporación de validaciones funcionales derivadas de las historias de usuario.
- Consolidación de prototipos de interfaz para campañas, leads y métricas comerciales.

## Artefactos consolidados

- `docs/ux/prototipos.md` — **R5.3** / **D1.5** Prototipos de IU
- `docs/arquitectura/c4-contenedores.md` — **D1.1** Diagrama de Contenedores
- `docs/arquitectura/api-modulo.md` — API del Módulo
- `docs/db/modelo-er.md` — **D1.4** Modelo ER
- `docs/db/schema.sql` — **D1.4** Schema SQL
- `entregas/entrega-03.md`

## Cambios principales

- Se refinó el modelo de dominio conceptual del módulo de Marketing.
- Se incorporaron atributos faltantes en `Campaña`:
  - `fechaInicio`
  - `fechaFin`
  - `presupuesto`
  - `costo`
  - `estado`
- Se consolidó `/Metricas` como atributo derivado en `Campaña`.
- Se agregó la entidad `Contacto` con relación 1 a 1 respecto de `Lead`.
- Se modelaron relaciones muchos-a-muchos:
  - `Campaña ↔ Producto`
  - `Lead ↔ Producto`
  - `Venta ↔ Producto`
- Se alinearon las historias de usuario con el modelo de dominio y el pipeline comercial definido.
- Se ajustaron las HU para eliminar inconsistencias respecto de métricas persistidas en campañas.
- Se redefinió el registro de campañas utilizando atributos reales del dominio:
  - fechas
  - presupuesto
  - costo
  - estado
  - productos asociados
- Se ajustó la HU de campañas para tratar las métricas como información derivada y no como datos obligatorios de creación.
- Se actualizó la HU de asociación de leads incorporando:
  - empresa (atributo String → refactorizado a entidad `Empresa` en iteración 06)
  - contacto
  - producto de interés
  - canal de origen
  - estado inicial
- Se alineó el pipeline comercial de leads con el modelo de dominio:
  - Contacto
  - Análisis
  - Consultoría
  - Demo
  - Presupuesto
  - Cierre
- Se removieron referencias no modeladas explícitamente, como historial persistente de etapas o auditorías avanzadas.
- Se incorporó trazabilidad entre:
  - campañas
  - leads
  - oportunidades
  - ventas
- Se definieron endpoints REST para campañas, leads, pipeline y cierre comercial.
- Se generó el esquema relacional completo (`schema.sql`) consistente con el modelo ER y el modelo de dominio.
- Se consolidaron prototipos en Figma para:
  - dashboard comercial
  - listado de campañas
  - creación de campañas
  - gestión de leads
  - visualización de métricas
- Se incorporaron flujos visuales consistentes con las validaciones funcionales definidas en las HU.
- Se alinearon los formularios de campañas y leads con los atributos definidos en el modelo de dominio y la API.
- Se validó visualmente el flujo de avance del pipeline comercial desde la interfaz.
- Se agrego sección de gestion de Leads en el prototipo
- Se creó el diagrama Egon de historias de dominio, con su respectivo archivo .egn y .png. Y se les hizo las correspondientes referencias en `historias-dominio.md`
- Se corrigieron HU-07 y HU-21: Se alinearon los ejemplos a los atributos del modelo de dominio.
- Se unificó el enfoque del MVP entre el archivo `historias-dominio.md` y `vision-modulo.md`.
## Decisiones tomadas

- Los estados de `Campaña` y `Lead` se modelan como atributos simples y no como entidades independientes.
- Las métricas de campaña no se almacenan físicamente; se calculan a partir de los datos comerciales registrados.
- `canalOrigen` se persiste como atributo obligatorio del lead.
- `Contacto` se modela como tabla separada para encapsular información de contacto.
- Se mantuvo la integración conceptual con el módulo de Ventas mediante:
  - `Oportunidad`
  - `Venta`
- El modelo relacional prioriza simplicidad y trazabilidad sobre normalización avanzada.
- El MVP no contempla automatización, IA ni integraciones externas activas.
- El cálculo avanzado de ROI queda fuera de alcance en esta iteración.
- Los prototipos priorizan navegación simple y visibilidad del pipeline comercial.

## Riesgos y pendientes

- Definir cálculo definitivo de ROI y métricas comerciales avanzadas.
- Evaluar necesidad de historial de cambios de estado del lead para auditoría.
- Confirmar integración futura con CRM externo.
- Validar restricciones de acceso y permisos sobre reportes comerciales.
- Definir atributos finales del módulo de Ventas para `Oportunidad` y `Venta`.
- Evaluar exposición futura de historial comercial vía API.
- Validar usabilidad de los prototipos con usuarios reales del área comercial.

## Próximos pasos

- Implementar persistencia física sobre PostgreSQL.
- Desarrollar endpoints REST definidos en `api-modulo.md`.
- Implementar validaciones de negocio del pipeline comercial.
- Integrar el módulo de Marketing con el módulo de Ventas.
- Incorporar testing automatizado de reglas funcionales y validaciones.
- Evolucionar métricas derivadas y reportes comerciales.
- Transformar prototipos Figma en componentes funcionales de frontend.