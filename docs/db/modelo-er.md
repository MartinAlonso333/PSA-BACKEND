# D1.4 - Modelo ER

## Alcance del modelo

* Cubre el módulo de **Marketing** del sistema PSA.
* Incluye las entidades necesarias para gestionar campañas, leads y sus relaciones con productos y contactos.
* Permite registrar métricas derivadas de campaña (leads generados, conversión, efectividad) y soportar el cálculo de ROI a partir de costos e ingresos asociados.
* Refleja el ciclo de vida del lead desde su captación hasta el estado `CONVERTIDO`, que es el puente de trazabilidad con el módulo de Ventas.
* La trazabilidad comercial se completa mediante el identificador del lead que Ventas recibe al alcanzar el estado `CONVERTIDO`.

## Supuestos de persistencia

* El estado de la campaña se persiste como atributo `estado` en la tabla `CAMPAÑA` (`Planificada`, `Activa`, `Finalizada`).
* El estado del lead se persiste como atributo `etapa` en la tabla `LEAD` (`Nuevo`, `Contactado`, `Calificado`, `Convertido`, `Perdido`).
* `canal` se persiste como FK a la tabla `CANAL`, con atributo obligatorio en `LEAD`.
* Las métricas de campaña (`/Metricas`) son derivadas y no se almacenan físicamente.
* `CONTACTO` se modela como tabla separada con relación 1 a 1 con `LEAD`.
* Una campaña puede promocionar múltiples productos y un producto puede pertenecer a múltiples campañas.
* Un lead puede estar interesado en múltiples productos y un producto puede interesar a múltiples leads.
* Un lead convertido (`CONVERTIDO`) es recibido por el módulo de Ventas, que gestiona la oportunidad comercial.
* `OPORTUNIDAD` y `CLIENTE` pertenecen al módulo de Ventas y no son persistentes en el esquema de Marketing.

## Entidades y relaciones

```mermaid
erDiagram
    CAMPAÑA (tabla: campania) {
        bigint id PK
        varchar nombre
        date fecha_inicio
        date fecha_fin
        decimal presupuesto
        decimal costo
        decimal roi
        varchar estado
        datetime creado_en
        datetime actualizado_en
    }

    CANAL {
        bigint id PK
        varchar nombre
        varchar tipo
    }

    CAMPAÑA_CANAL (tabla: campania_canal) {
        bigint campaña_id FK
        bigint canal_id FK
    }

    EMPRESA {
        bigint id PK
        varchar nombre
        varchar razon_social
        varchar cuit
        varchar direccion
    }

    LEAD (tabla: lead_comercial) {
        bigint id PK
        varchar nombre
        bigint empresa_id FK
        varchar etapa
        bigint canal_id FK
        bigint canal_anterior_id FK
        bigint campaña_id FK
        varchar resultado_cierre
        varchar motivo_perdida
        datetime creado_en
        datetime actualizado_en
    }

    CONTACTO {
        bigint id PK
        bigint lead_id FK
        varchar email
        varchar telefono
    }

    PRODUCTO {
        bigint id PK
        varchar nombre
        varchar codigo
    }

    CAMPAÑA_PRODUCTO (tabla: campania_producto) {
        bigint campaña_id FK
        bigint producto_id FK
    }

    LEAD_PRODUCTO {
        bigint lead_id FK
        bigint producto_id FK
    }

    CAMPAÑA ||--o{ CAMPAÑA_CANAL : "utiliza"
    CAMPAÑA_CANAL }o--|| CANAL : "referencia"

    LEAD }o--|| CANAL : "proviene_de"
    LEAD }o--o| CANAL : "canal_anterior"

    CAMPAÑA ||--o{ LEAD : "genera"

    EMPRESA ||--o{ LEAD : "representa_a"

    CAMPAÑA ||--o{ CAMPAÑA_PRODUCTO : "promociona"
    CAMPAÑA_PRODUCTO }o--|| PRODUCTO : "referencia"

    LEAD ||--|| CONTACTO : "tiene"

    LEAD ||--o{ LEAD_PRODUCTO : "interesado en"
    LEAD_PRODUCTO }o--|| PRODUCTO : "referencia"
```

## Reglas relevantes de datos

| Regla                                                               | Impacto en el esquema                                |
| ------------------------------------------------------------------- | ---------------------------------------------------- |
| Todo lead debe estar asociado a una campaña y un canal de origen    | `campaña_id` y `canal_id` son NOT NULL en `LEAD` |
| Una empresa representa a uno o más leads (0..1 empresa representa a * lead) | `empresa_id` es FK nullable en `LEAD` |
| Toda campaña debe tener nombre, fechas, presupuesto, costo y estado | Atributos obligatorios en `CAMPAÑA`                  |
| Las métricas de campaña son derivadas                               | No existe tabla `METRICA`                            |
| Cada lead tiene un único conjunto de datos de contacto              | Relación 1 a 1 entre `LEAD` y `CONTACTO`             |
| Una campaña puede promocionar múltiples productos                   | Tabla intermedia `CAMPAÑA_PRODUCTO`                  |
| Un lead puede interesarse en múltiples productos                    | Tabla intermedia `LEAD_PRODUCTO`                     |
| Un lead convertido (`CONVERTIDO`) es el puente con Ventas          | El estado `CONVERTIDO` en `LEAD` indica que pasa a Ventas |

## Índices JPA

Se agregaron `@Index` en las entidades JPA para optimizar consultas frecuentes:

| Entidad | Índices |
|---------|---------|
| `Campania` | `idx_campania_nombre`, `idx_campania_estado`, `idx_campania_fecha_inicio`, `idx_campania_fecha_fin` |
| `LeadComercial` | `idx_lead_campania_id`, `idx_lead_canal_id`, `idx_lead_empresa_id`, `idx_lead_etapa` |

## Correspondencia con implementación

| Entidad ER | Tabla en BD / JPA | Notas |
|---|---|---|
| CANAL | canal | Nueva entidad creada a partir del atributo como String |
| CAMPAÑA | campania | El código omite la tilde (ñ → n) y usa minúsculas |
| LEAD | lead_comercial | Se antepuso lead_ para distinguir del concepto genérico de "lead" |
| EMPRESA | empresa | Nueva entidad creada a partir del atributo como String |
| CAMPAÑA_PRODUCTO | campania_producto | Tabla intermedia ManyToMany |
| CAMPAÑA_CANAL | campania_canal | Tabla intermedia ManyToMany |
| LEAD_PRODUCTO | lead_producto | Tabla intermedia ManyToMany |
| CONTACTO | contacto | Coincide exactamente |
| PRODUCTO | producto | Coincide exactamente |
| OPORTUNIDAD | *(módulo Ventas)* | Entidad externa, no persistida en Marketing |
| CLIENTE | *(módulo Ventas)* | Entidad externa, no persistida en Marketing |

## Estados de negocio

### LEAD

* Nuevo
* Contactado
* Calificado
* Convertido
* Perdido

## Decisiones pendientes

* Definir si se requiere historial de cambios de estado del lead para auditoría.
* Precisar cómo se calculará el ROI utilizando datos de campañas y ventas (integración con módulo Ventas).
* Confirmar si `PRODUCTO` será una referencia local o compartida con Ventas.
