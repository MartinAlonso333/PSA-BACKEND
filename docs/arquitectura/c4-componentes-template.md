# C4 - Componentes

Usar Mermaid inline con sintaxis `C4Component`.

## Propósito

...

## Componentes principales

| Componente | Responsabilidad | Interacciones |
| --- | --- | --- |
| ... | ... | ... |

## Diagrama

```mermaid
C4Component
    title Componentes del módulo
    Container_Boundary(api, "API del módulo") {
        Component(controller, "Controller", "Spring MVC", "Recibe requests")
        Component(service, "Service", "Java", "Orquesta reglas")
        Component(repository, "Repository", "Spring Data", "Accede a persistencia")
    }
    ComponentDb(db, "Base de datos", "SQL", "Persistencia")

    Rel(controller, service, "Invoca")
    Rel(service, repository, "Usa")
    Rel(repository, db, "Lee y escribe")
```

## Riesgos o límites del diseño

- ...
