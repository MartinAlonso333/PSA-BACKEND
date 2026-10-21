# Modelo ER

Usar Mermaid inline con sintaxis `erDiagram`.

## Alcance del modelo

...

## Supuestos de persistencia

- ...

## Entidades y relaciones

```mermaid
erDiagram
    ENTIDAD_PRINCIPAL {
        bigint id PK
        string nombre
        datetime creado_en
    }

    ENTIDAD_RELACIONADA {
        bigint id PK
        bigint entidad_principal_id FK
        string estado
    }

    ENTIDAD_PRINCIPAL ||--o{ ENTIDAD_RELACIONADA : contiene
```

## Reglas relevantes de datos

| Regla | Impacto en el esquema |
| --- | --- |
| ... | ... |

## Decisiones pendientes

- ...
