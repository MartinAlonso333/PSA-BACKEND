# AGENTS.md

Este repositorio ya fue inicializado y configurado.

## Reglas

- No ejecutar `/init` ni otro inicializador que cree, reemplace o regenere `AGENTS.md`.
- No reemplazar ni regenerar este archivo (`AGENTS.md`).
- No modificar archivos en `iteraciones/`, `.github/` ni archivos `*-template`.
- Guardar manifiestos de entrega en `entregas/`.
- Mantener la documentación viva en `docs/` y la implementación backend en `src/`.

## Criterio general

- Copiar o derivar cada template a su archivo vivo correspondiente, sin editar el template original.
- Mantener separación clara entre `controller`, `service`, `domain`, `repository` y `config`.
- Los archivos `*.feature` deben ser ejecutables con Cucumber JVM, no sólo documentales.
- Las minutas viven en `docs/descubrimiento/minutas/` y pueden tener fecha en el nombre.
- Para diagramas soportados, usar Mermaid inline en el Markdown como fuente principal.
