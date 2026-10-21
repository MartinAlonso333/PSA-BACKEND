# C4 - Contexto

Usar Mermaid inline con sintaxis `C4Context`.

## Propósito

...

## Sistema analizado

...

## Actores y sistemas externos

| Elemento | Tipo | Interacción |
| --- | --- | --- |
| ... | ... | ... |

## Diagrama

```mermaid
C4Context
    title Contexto del módulo
    Person(usuario, "Usuario", "Interactúa con el módulo")
    Person(admin, "Administrador", "Gestiona el módulo")
    System_Boundary(modulo_boundary, "Módulo") {
        System(modulo, "Módulo", "Responsabilidad principal del squad")
    }
    System_Ext(externo, "Sistema externo", "Intercambio de información")

    Rel(usuario, modulo, "Usa")
    Rel(admin, modulo, "Administra")
    Rel(modulo, externo, "Consulta / sincroniza")
```

## Notas

- ...
