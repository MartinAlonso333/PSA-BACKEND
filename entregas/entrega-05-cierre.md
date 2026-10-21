# Entrega 05 - Demo y Defensa Grupal

## Cambios implementados en esta iteración

### Campañas
- Se agregó soporte de **canales de difusión** en creación y edición de campañas (checkboxes múltiples en frontend, campo `canales: List<Long>` en backend).
- Se agregó **validación de fechas** (inicio ≤ fin) en creación y edición, tanto en frontend como en backend.
- Se agregó endpoint **`DELETE /api/campanias/{id}`** con validación: no se puede eliminar si tiene leads asociados.
- Se agregó botón de **eliminar campaña** en la vista de detalle/edición con confirmación.

### Leads
- Se agregó **campaña de origen** (`campaniaOrigenId`) y **canal de origen** como campos editables en edición de leads.
- Al cambiar la campaña, se filtran los canales disponibles (misma lógica que en creación).
- Se agregó endpoint **`DELETE /api/leads/{id}`** con restricción: solo se pueden eliminar leads en etapa NUEVO, CONTACTADO o CALIFICADO.
- Se agregó botón de **eliminar lead** en la vista de detalle/edición con confirmación.

### Dashboard
- Se eliminó la columna "Efectividad" del listado de campañas.
- Se ajustó el botón de editar para mantener consistencia visual con el módulo de Leads.

### Generales
- Se corrigió el título del sistema de "Modulo Marketing prototipo" a "Sistema PSA".
- Se corrigió la extracción de mensajes de error del backend (cambio de `body.message` a `body.error` en `client.ts`).

### Historias de usuario priorizadas
- Se agregaron **HU-08** y **HU-14** a la tabla de historias priorizadas en `docs/requisitos/historias-usuario.md`, alineando con la visión de la Tribu B que requiere ROI y métricas de conversión como parte del MVP.
- Se actualizó `docs/requisitos/matriz-trazabilidad.md` con las filas de HU-08 y HU-14, tanto en la matriz HDU vs minutas como en la trazabilidad de implementación del Módulo Marketing.

### Cobertura BDD
- Se creó `src/test/resources/features/HU-08.feature` con 3 escenarios para validar el cálculo de ROI:
  - Cálculo de ROI en campaña con leads convertidos
  - Métricas de campaña sin leads registrados
  - Métricas de campaña sin leads convertidos
- Se creó `src/test/resources/features/HU-14.feature` con 3 escenarios para validar las métricas de efectividad:
  - Visualización con leads en múltiples estados
  - Visualización con conversión completa
  - Visualización sin leads
- Se creó `src/test/java/.../bdd/steps/MetricasSteps.java` con los steps compartidos para ambas features.
- Los tests se ejecutan con `./gradlew check` — **155 tests, 0 fallos** (pasó de 150 a 155).

## Objetivo de la defensa oral

Demostrar que el Módulo de Marketing del sistema PSA cumple con el flujo completo de captación comercial: desde el registro de campañas y leads hasta el pipeline de conversión, integrado con el frontend de TRIBU y el backend de Ventas del Squad 3. Se espera mostrar:

- Backend funcional desplegado en Render con endpoints REST documentados en Swagger.
- Frontend React SPA desplegado que consume los endpoints en vivo.
- Flujo end-to-end: crear campaña → registrar lead → avanzar pipeline → cerrar como ganado/perdido → consultar métricas.
- Trazabilidad completa entre campañas, leads, canales y productos.
- ROI calculado integrando datos del módulo de Ventas.

## Guion de demo

1. **Abrir frontend** (`https://two026-1c-tpg-tribu-b.onrender.com`) — mostrar dashboard sin datos o con datos semilla.
2. **Crear una campaña** — completar nombre, fechas, presupuesto, costo, seleccionar productos y canales. Mostrar validación de fechas (inicio ≤ fin).
3. **Ver campaña creada** en el listado del dashboard, estado "Planificada".
4. **Activar la campaña** — cambiar estado a "Activa" desde el detalle.
5. **Registrar un lead** — seleccionar campaña activa, completar nombre, canal de origen, contacto (email/teléfono), empresa, productos de interés. Mostrar filtro de canales disponibles.
6. **Avanzar pipeline** — llevar el lead por Nuevo → Contactado → Calificado → Convertido. Mostrar que no permite saltos de etapa.
7. **Cerrar lead como Ganado** — mostrar resultado y métricas de campaña actualizadas (conversión, ROI).
8. **Crear un segundo lead y cerrarlo como Perdido** con motivo — mostrar el reporte de leads por canal.
9. **Cerrar lead como Perdido** — mostrar estado de leads y figure como perdido.
10. **Eliminar un lead en etapa NUEVO** — mostrar restricción (no permite eliminar convertidos/perdidos).
11. **Consultar métricas de campaña** — leads generados, convertidos, % conversión, monto ganado total, ROI.

## Reparto entre integrantes

| Integrante | Rol en la defensa |
|---|---|
| **Integrante 1** | Ape Demo en vivo: contexto del módulo de Marketing, arquitectura general, decisiones técnicas, crear campaña, activar, registrar lead, avanzar pipeline, cierre de leads, métricas, reportes, Swagger; cierre y conclusiones |

## Logística de la exposición

- **Duración total estimada**: 40–45 minutos (demo 8–10 min + preguntas).
- **Backend ya debe estar despierto** — pegarle al health check 1 min antes de empezar para evitar cold start de Render.
- **Navegador preparado** con pestañas: frontend, Swagger, cron-job (keep-awake).
- **Sonido y micrófono** verificados antes de comenzar.

## Riesgos de exposición

| Riesgo | Mitigación |
|---|---|
| Render duerme el backend (cold start ~30s) | Hacer health check 1 min antes. Mantener cron-job activo. |
| Base de datos PostgreSQL free expirada (90 días) | Verificar fecha de expiración. Tener dump .sql de respaldo. |
| Error de CORS o red durante la demo | Tener Postman local como respaldo para llamar directo al backend. |
| Frontend caído o con errores de build | Tener capturas de pantalla del flujo y Postman. |
| Conexión a Internet inestable | Video grabado del flujo completo como respaldo. |
| Backend de Ventas (Squad 3) caído | La demo de métricas de campaña funciona sin Ventas (ROI = null). |

## Checklist previo

- [ ] Health check del backend responde `{"status":"ok"}`.
- [ ] Frontend carga correctamente y muestra el dashboard.
- [ ] Swagger UI accesible en `/docs`.
- [ ] Datos semilla presentes (canales, productos, empresas).
- [ ] Base de datos no expirada (verificar en Render).
- [ ] Cron-job de keep-awake funcionando.
- [ ] Pantalla compartida, audio y micrófono verificados.
- [ ] Reparto de roles confirmado entre integrantes.
- [ ] Conexión a Internet verificada.
