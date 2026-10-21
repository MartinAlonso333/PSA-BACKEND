# Historias de Dominio

Este artefacto documenta las historias de dominio del TPG y fija un contrato estable para su evolución. La herramienta explícita para modelarlas es Egon (`https://egon.io/`) y la fuente editable canónica es el archivo `.egn`.

## Fuente canónica y companions

- herramienta explícita: Egon (`https://egon.io/`)
- fuente editable canónica: `docs/requisitos/historias-dominio.egn`
- export visual obligatoria: `docs/requisitos/historias-dominio.svg` o `docs/requisitos/historias-dominio.png`
- documento de contexto y trazabilidad: `docs/requisitos/historias-dominio.md`
- no usar filenames fechados para este artefacto en el repo del squad

## Propósito

Explicar el problema del módulo desde la lógica del dominio y no desde la interfaz o la implementación.

## Criterio de recorte

Describir qué parte del dominio queda cubierta por estas historias y qué parte queda fuera del MVP. Aclarar el recorte aplicado en términos de actores, objetos de trabajo, reglas y eventos que sí entran en el alcance.

## Actores y objetos de trabajo relevantes

Listar actores, objetos de trabajo y eventos del dominio que participan en las historias. Para cada uno, indicar por qué importa para el problema y qué tensión o necesidad expresa.

## Historias relevadas

Usar una historia por bloque y explicitar:

- actores;
- disparador;
- necesidad o tensión;
- resultado esperado;
- regla o restricción relevante;
- objeto de trabajo involucrado.

## Export visual

Insertar o referenciar la exportación visual generada desde Egon. La imagen debe acompañar al texto y reflejar el mismo recorte conceptual.

## Relación con requisitos posteriores

Indicar cómo estas historias ayudan a derivar historias de usuario, escenarios Gherkin, reglas de negocio y el modelo de dominio conceptual.

## Dudas abiertas

Listar supuestos, ambigüedades y decisiones pendientes que todavía afecten el recorte o la trazabilidad.
