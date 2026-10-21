# Entrega 02 - Visión, análisis y especificación
## Entrega y alcance

- Consolidación de la visión funcional, análisis de dominio y especificación conceptual del módulo de Marketing del sistema PSA.
- Definición del alcance funcional del MVP orientado a campañas, leads y trazabilidad comercial.
- Formalización del modelo de dominio conceptual y su relación con requisitos, historias y escenarios.

## Iteraciones cubiertas

- Iteración 02
- Iteración 03

## Artefactos consolidados

# Iteracion 01 (R1 + R2).

- `docs/descubrimiento/onion-model.md` — **R1.2** Onion Model
- `docs/descubrimiento/onion-model.png` — **R1.2** Onion Model (imagen)
- `docs/descubrimiento/onion-model.drawio` — **R1.2** Onion Model (draw.io)
- `docs/descubrimiento/organigrama.md` — **R1.1** Organigrama
- `docs/descubrimiento/organigrama.png` — **R1.1** Organigrama (imagen)

# Iteracion 02 (R3 + R4).

- `docs/requisitos/historias-dominio.md` — **R3** Historias de Dominio
- `docs/requisitos/especificacion-requisitos.md` — **R4** Especificación de Requisitos
- `docs/requisitos/historias-usuario.md` — **R4.1** Historias de Usuario
- `docs/requisitos/matriz-trazabilidad.md` — **R4.2** Matriz de Trazabilidad

# iteracion 03 (V1 + R5).

- `docs/vision/vision-modulo.md` — **V1** Visión del Módulo
- `docs/analisis/c4-contexto.md` — **R5.1** Diagrama de Contexto
- `docs/analisis/modelo-dominio.md` — **R5.2** Modelo de Dominio
- `docs/analisis/modelo-dominio.puml` — **R5.2** Modelo de Dominio (PlantUML)
- `docs/analisis/modelo-dominio.png` — **R5.2** Modelo de Dominio (imagen)
- `docs/analisis/diccionario-datos.md` — **R5.2** Diccionario de Datos
- `docs/ux/prototipos.md` — **R5.3** / **D1.5** Prototipos de IU

## Cambios principales

- Trazabilidad entre nuevas entrevistas e iteraciones anteriores.
- Consolidación de la visión funcional del módulo de Marketing.
- Definición del flujo comercial principal:
  - Campaña → Lead → Oportunidad → Negociación → Ganada/Perdida.
- Incorporación de métricas de efectividad y ROI dentro del alcance del MVP.
- Refinamiento de estados básicos para campañas y leads.
- Formalización del modelo de dominio conceptual en PlantUML.
- Incorporación de trazabilidad entre campañas, leads y oportunidades.
- Refinamiento del diccionario de datos y términos del dominio.
- Consolidación de escenarios Gherkin alineados con historias de usuario priorizadas.

## Decisiones tomadas

- Mantener el MVP enfocado en funcionalidades esenciales de Marketing.
- Delegar el seguimiento comercial posterior al lead al módulo/equipo de Ventas.
- Implementar únicamente estados básicos para campañas y leads.
- Posponer automatizaciones avanzadas, integraciones complejas y seguridad detallada.
- Mantener el modelo de dominio en un nivel conceptual, sin detalles de implementación técnica.
- Utilizar PlantUML como fuente canónica editable del modelo de dominio.

## Riesgos y pendientes

- Definir con mayor precisión el cálculo detallado del ROI.
- Refinar futuras integraciones con sistemas CRM externos.
- Profundizar el flujo posterior a oportunidades ganadas.
- Definir permisos y roles para futuras iteraciones.
- Evaluar necesidades de automatización y alertas.
- Validar reportes adicionales requeridos por Marketing y Ventas.


## Próximos pasos

- Continuar refinando el modelo funcional y de dominio.
- Avanzar hacia especificaciones más cercanas a implementación.
- Evaluar integraciones futuras con CRM y automatizaciones.
- Refinar backlog y priorización para futuras iteraciones.
- Expandir escenarios BDD y criterios de aceptación.
- Preparar la transición hacia actividades de implementación.