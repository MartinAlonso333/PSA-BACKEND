# Modelo de dominio conceptual

Este artefacto documenta el modelo de dominio conceptual del módulo, con foco en conceptos del problema y sus relaciones. Para este archivo hay una excepción explícita a la regla general de Mermaid del repo: la fuente canónica editable es PlantUML.

## Fuente canónica y companions

- fuente canónica editable: `docs/analisis/modelo-dominio.puml`
- export visual obligatoria: `docs/analisis/modelo-dominio.svg` o `docs/analisis/modelo-dominio.png`
- documento de contexto y trazabilidad: `docs/analisis/modelo-dominio.md`
- no usar `drawio`, Mermaid inline ni una imagen suelta como fuente principal de este artefacto

## Alcance del modelo

Explicar qué recorte del dominio cubre este modelo en función del MVP del módulo y de las historias priorizadas. Aclarar qué parte del problema sí entra en Iteración 03 y qué parte queda explícitamente afuera.

## Inputs usados

Listar y citar explícitamente, como mínimo:

- `docs/vision/vision-modulo.md`
- `docs/requisitos/historias-dominio.md`
- `docs/requisitos/historias-usuario.md`
- `docs/requisitos/matriz-trazabilidad.md`
- `src/test/resources/features/HU-*.feature` de las historias priorizadas

Si hace falta agregar apoyo, sumar `docs/requisitos/especificacion-requisitos.md` u otra evidencia del squad, pero sin reemplazar los insumos anteriores.

## Historias priorizadas tomadas como base

| Historia | Recorte que aporta al modelo | Escenarios o evidencia usada |
| --- | --- | --- |
| `HU-01` | ... | `src/test/resources/features/HU-01.feature`, matriz, visión |
| `HU-02` | ... | `src/test/resources/features/HU-02.feature`, historias de dominio |

## Clases conceptuales

| Clase conceptual | Evidencia visible | Justificación breve |
| --- | --- | --- |
| `Reserva` | `HU-01`, historia de dominio 2 | es un objeto del problema, no una pantalla ni una tabla |
| `Usuario` | `HU-01`, matriz de trazabilidad | participa de decisiones relevantes del flujo |

## Asociaciones

| Relación | Multiplicidad | Etiqueta | Evidencia visible |
| --- | --- | --- | --- |
| `Usuario` - `Reserva` | `1` a `0..*` | `realiza` | `HU-01`, escenario principal |

## Atributos

| Clase | Atributos simples | Evidencia visible |
| --- | --- | --- |
| `Reserva` | `codigo`, `fechaCreacion` | feature y requisito trazado |
| `Usuario` | `nombre`, `mail` | visión y reglas del módulo |

## Diagrama

![Modelo de dominio](modelo-dominio.svg)

## Trazabilidad

| Elemento del modelo | Historias / features | Evidencia adicional |
| --- | --- | --- |
| `Reserva` | `HU-01`, `HU-01.feature` | `docs/requisitos/matriz-trazabilidad.md`, visión |
| `Usuario realiza Reserva` | `HU-01` | historia de dominio, matriz |

## Fuera de alcance y exclusiones

- no modelar endpoints, controllers, services, repositories, DTOs ni decisiones de arquitectura
- no modelar pantallas, widgets, botones ni navegación de UI
- no convertir esto en modelo ER, schema SQL ni diagrama de código
- no inventar clases o asociaciones sin evidencia visible en los inputs anteriores

## Dudas abiertas

- registrar conflictos entre historias, features, trazabilidad y visión
- si una relación necesita atributos propios, justificar clase asociativa o dejar la duda explícita
- anotar supuestos temporales o vocabulario todavía inestable

## Subset permitido de PlantUML

Usar `docs/analisis/modelo-dominio.puml` con un diagrama de clases conceptual al estilo Larman. El subset permitido para este artefacto es:

- `class`
- atributos simples
- asociaciones con multiplicidad y etiqueta
- generalización solo si realmente aclara el dominio
- clase asociativa cuando la relación necesita atributos propios

Queda prohibido:

- métodos u operaciones
- visibilidades (`+`, `-`, `#`, `~`)
- `!include`
- `skinparam`
- interfaces, packages o estereotipos técnicos
- vocabulario de implementación como `Controller`, `Service`, `Repository`, `DTO`, `API`, `endpoint`, `tabla`, `screen` o `UI`
