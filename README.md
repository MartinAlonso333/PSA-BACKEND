# FIUBA IS1 2026 1C - TPG Squad

Repositorio template para la assignment grupal `2026-1c-tpg-squad` de GitHub Classroom.

GitHub Classroom usa este repositorio para generar el starter repo docente del assignment. A partir de ese starter repo se derivan luego los repos de los squads.

Cada squad trabaja durante toda la cursada sobre su propio repositorio derivado de este template. El repositorio es acumulativo: no se recrea por entrega, sino que evoluciona durante las seis iteraciones del Trabajo Práctico Grupal.

## Mapa de entregas

El trabajo interno del squad se organiza en 6 iteraciones, pero las entregas académicas quedan agrupadas así:

- `Entrega 0`: armado del squad y contrato inicial. Ya ocurrió el `2026-03-30`.
- `Entrega 1`: Iteración 1. Deadline `2026-04-13 12:00 ART`.
- `Entrega 2`: Iteraciones 2 y 3. Deadline `2026-05-11 12:00 ART`.
- `Entrega 3`: Iteración 4. Deadline `2026-05-25 12:00 ART`.
- `Entrega 4`: Iteraciones 5 y 6. Deadline `2026-06-22 12:00 ART`.
- `Entrega 5`: cierre, demo y defensa oral grupal. Se prepara en paralelo al cierre técnico.

## Estructura

- `AGENTS.md`: reglas fijas para trabajar dentro del repositorio.
- `iteraciones/`: enunciados y foco esperado por iteración.
- `entregas/`: manifiestos formales por entrega y bitácoras internas opcionales por iteración.
- `docs/vision/`: visión del módulo y alcance del MVP.
- `docs/descubrimiento/`: organigrama, onion model y minutas.
- `docs/requisitos/`: historias de dominio, especificación, backlog y trazabilidad.
- `docs/analisis/`: contexto, modelo de dominio y diccionario de datos.
- `docs/ux/`: prototipos significativos.
- `docs/arquitectura/`: contenedores, componentes, diagrama de código y API.
- Swagger UI queda disponible en `/docs` y el JSON OpenAPI en `/v3/api-docs` (`/swagger-ui.html` sigue como alias de compatibilidad).
- `docs/db/`: modelo ER y esquema SQL.
- `src/`: backend Spring Boot, pruebas unitarias y pruebas de aceptación.
- `scripts/`: preflights locales por iteración.
- `.github/workflows/`: validaciones automáticas.

## Regla de trabajo

Los docentes controlan `AGENTS.md`, `iteraciones/`, los archivos `*-template`, `.github/` y los scripts de validación.

Los squads crean y mantienen los artefactos vivos derivados de esos templates, además del código, las pruebas y las decisiones de diseño del módulo.

## Convención general

- No ejecutar `/init` ni regenerar `AGENTS.md`.
- No modificar archivos docentes ni archivos `*-template`.
- Copiar o derivar cada template a su archivo vivo sin el sufijo `-template`.
- Mantener nombres estables para los artefactos vivos: la evolución queda en git, no en el filename.
- Las minutas sí pueden llevar fecha en el nombre del archivo dentro de `docs/descubrimiento/minutas/`.
- Para diagramas soportados, priorizar Mermaid inline dentro del propio Markdown.
- Excepción explícita: `docs/requisitos/historias-dominio.*` usa Egon (`https://egon.io/`) como fuente canónica editable (`docs/requisitos/historias-dominio.egn`) y una exportación `svg` o `png` referenciada desde `docs/requisitos/historias-dominio.md`.
- Excepción explícita: `docs/analisis/modelo-dominio.*` usa PlantUML (`docs/analisis/modelo-dominio.puml`) como fuente canónica editable y una exportación `svg` o `png` referenciada desde `docs/analisis/modelo-dominio.md`.
- Antes de hacer push, ejecutar el preflight de la iteración en curso.
- Desde `iteracion-05` en adelante conviene correr `./gradlew check` localmente de forma frecuente.

## Feedback

GitHub Classroom crea un pull request especial titulado `Feedback` para que los docentes dejen comentarios sobre el trabajo del squad.

Ese pull request no debe cerrarse. En repositorios recién creados suele aparecer como `#1`, pero la referencia estable es su título: `Feedback`.

## Actualizaciones docentes

Cuando los docentes publiquen una corrección técnica o una nueva iteración del material docente, el cambio entrará primero en `main`.

La operatoria esperada es:

- los docentes mergean el PR docente hacia `main`;
- luego anuncian por Slack que hay cambios nuevos disponibles;
- cada squad trae esos cambios desde `main` a su rama de trabajo antes de seguir.
