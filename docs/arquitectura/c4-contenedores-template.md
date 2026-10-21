# C4 - Contenedores

Usar Mermaid inline con sintaxis `C4Container`.

## Propósito

...

## Contenedores

| Contenedor | Responsabilidad | Tecnología |
| --- | --- | --- |
| ... | ... | ... |

## Diagrama

```mermaid
C4Container
    title Contenedores del módulo
    Person(usuario, "Usuario")
    System_Boundary(modulo_boundary, "Módulo") {
        Container(web, "API / Web App", "Spring Boot", "Expone funcionalidades del módulo")
        ContainerDb(db, "Base de datos", "SQL", "Persistencia del módulo")
    }
    System_Ext(externo, "Sistema externo", "Integración")

    Rel(usuario, web, "Usa")
    Rel(web, db, "Lee y escribe")
    Rel(web, externo, "Integra")
```

## Decisiones relevantes

- ...
