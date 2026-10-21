# Entrega 04 - Diseño, codificación, pruebas y despliegue

## Entrega y alcance

Consolidación final del módulo de Marketing del sistema PSA. Se completó la implementación del backend con todos los endpoints definidos, persistencia PostgreSQL, pruebas automatizadas, documentación completa y despliegue funcional en Render.

El módulo expone la gestión completa del pipeline comercial: campañas, leads, empresas, canales, productos, contacto y pipeline con trazabilidad end-to-end desde la captación hasta la conversión.

## Iteraciones cubiertas

- Iteración 05
- Iteración 06

## Artefactos consolidados

### Documentación

| Artefacto | Referencia | Descripción |
| --- | --- | --- |
| `docs/analisis/diccionario-datos.md` | **R5.2** | Diccionario de Datos |
| `docs/analisis/modelo-dominio.md` | **R5.2** | Modelo de Dominio |
| `docs/analisis/modelo-dominio.puml` | **R5.2** | Modelo de Dominio (PlantUML) |
| `docs/analisis/modelo-dominio.png` | **R5.2** | Modelo de Dominio (imagen) |
| `docs/arquitectura/c4-contenedores.md` | **D1.1** | Diagrama de Contenedores |
| `docs/arquitectura/c4-componentes.md` | **D1.2** | Diagrama de Componentes |
| `docs/arquitectura/diagrama-codigo.md` | **D1.3** | Diagrama de Código |
| `docs/arquitectura/api-modulo.md` | — | API del Módulo (contrato completo) |
| `docs/db/modelo-er.md` | **D1.4** | Modelo Entidad-Relación |
| `docs/db/schema.sql` | **D1.4** | Schema SQL |
| `docs/ux/prototipos.md` | **R5.3 / D1.5** | Prototipos de IU |
| `docs/requisitos/especificacion-requisitos.md` | **R4** | Especificación de Requisitos |
| `docs/requisitos/matriz-trazabilidad.md` | **R4.2** | Matriz de Trazabilidad |
| `docs/requisitos/historias-dominio.md` | **R3** | Historias de Dominio |

### Código y pruebas

| Artefacto | Descripción |
| --- | --- |
| `src/main/java/ar/edu/fiuba/is1/tpg/` | Código backend completo (controllers, services, domain, repositories, DTOs, config, exceptions) |
| `src/test/java/` | Pruebas unitarias y de aceptación |
| `src/test/resources/features/` | Archivos `.feature` Cucumber (HU-06, HU-07, HU-22, HU-24, HU-25, HU-26, health, y features de soporte/proyectos/ventas) |

### Infraestructura y despliegue

| Artefacto | Descripción |
| --- | --- |
| `Dockerfile` | Multi-stage Docker para build y runtime |
| `build.gradle` | Build con Spring Boot 3.3.5, Java 21, PostgreSQL, Swagger/OpenAPI |
| `src/main/resources/application.properties` | Configuración parametrizada por env vars (DB, CORS, JPA) |
| `src/main/java/.../config/AppConfig.java` | CORS configurable por `CORS_ALLOWED_ORIGINS` |

## Cambios principales

### Iteración 05 — Features N3 y pruebas de aceptación

- Se implementaron los endpoints REST completos para el módulo de Marketing:
  - CRUD de campañas con filtros por estado y obtención de métricas
  - CRUD de leads con filtros por campaña, canal y etapa
  - Pipeline comercial con avance secuencial de etapas (NUEVO → CONTACTADO → CALIFICADO → CONVERTIDO / PERDIDO)
  - Cierre de leads con resultado GANADO / PERDIDO
  - CRUD de empresas con deduplicación por CUIT
  - Endpoints read-only para canales y productos
  - Reporte de leads agrupados por canal
- Se incorporó el estado "Perdido" en el flujo de vida de los leads, trazado en toda la documentación.
- Se implementaron pruebas de aceptación con Cucumber para HU-06, HU-07, HU-22 y HU-24.
- Se agregó endpoint de health check (`GET /api/health`) y documentación Swagger/OpenAPI.
- Se actualizó `docs/arquitectura/c4-componentes.md` y `docs/arquitectura/diagrama-codigo.md` para reflejar la implementación real.

### Iteración 06 — Cierre, refinamiento y consolidación

- Se refactorizó `Empresa` de atributo String a entidad JPA propia con repositorio, service y controller.
- Se agregaron endpoints para modificar campañas y leads (PUT).
- Se agregó eliminación de campañas (DELETE).
- Se incorporaron canales asociados a campaña como dato de negocio.
- Se corrigió el modelo de dominio para alinearlo con la implementación real (relaciones, cardinalidades, atributos).
- Se eliminaron tablas del módulo de Ventas del schema SQL (no se persisten en nuestro módulo).
- Se ajustó la documentación de la API, el modelo ER y el diccionario de datos para mantener trazabilidad.
- Se configuró CORS parametrizado por variable de entorno (`CORS_ALLOWED_ORIGINS`).
- Se creó Dockerfile multi-stage para despliegue contenerizado.
- Se parametrizó la conexión a base de datos PostgreSQL mediante env vars (`DB_URL`, `DB_USER`, `DB_PASSWORD`).
- Se desplegó el backend en Render apuntando a la rama `iteracion-06` con deploy automático.
- Se agregaron escenarios BDD para PUT/DELETE campañas, GET leads (listado/detalle) y PUT lead en `HU-06.feature`, `HU-07.feature` y `HU-22.feature`.
- Se crearon nuevas features `HU-25.feature` (catálogos de productos y canales) y `HU-26.feature` (gestión de empresas) con sus steps correspondientes.
- Se agregaron índices JPA (`@Index`) en las entidades `Campania` y `LeadComercial` para optimizar consultas frecuentes.
- Se corrigió `LazyInitializationException` en `LeadService.listar()` y `LeadService.obtener()` agregando `@Transactional(readOnly = true)`.

## Decisiones tomadas

| Decisión | Justificación |
| --- | --- |
| Empresa como entidad JPA separada | Permite deduplicación por CUIT y trazabilidad con leads y oportunidades |
| `ddl-auto=update` en producción | Evita pérdida de datos en redeploys; los seeds corren una sola vez |
| CORS configurable por env var | Permite distintos origins según entorno (local, prod) sin tocar código |
| Métricas de campaña como atributos derivados | No se persisten; se calculan a partir de leads asociados |
| Pipeline con avance secuencial obligatorio | Evita saltos de etapa inconsistentes; cada etapa requiere la anterior |
| Health endpoint custom (sin Actuator) | Simple, liviano y suficiente para el smoke de demo |

## Evidencia de despliegue o ejecución demostrable

- **URL pública del backend**: `https://two026-1c-tpg-squad-1-tv35.onrender.com`
- **Health check**: `GET /api/health` → `{"status":"ok","message":"TPG squad listo"}`
- **Swagger UI**: `https://two026-1c-tpg-squad-1-tv35.onrender.com/docs`
- **OpenAPI spec**: `https://two026-1c-tpg-squad-1-tv35.onrender.com/v3/api-docs`
- **CORS verificado**: el frontend `https://two026-1c-tpg-tribu-b.onrender.com` está en la allowlist
- **Base de datos**: PostgreSQL en Render con datos semilla cargados (4 canales, productos, empresas, campañas y leads de prueba)
- **Deploy automático**: cada push a `iteracion-06` dispara redeploy en Render

## Riesgos y pendientes

- El plan free de Render **duerme** el servicio tras 15 min sin uso. Primer request tarda ~30s en despertar. Mitigado con cron-job cada 10 min.
- La base de datos PostgreSQL free **expira a los 90 días**. Verificar antes de la defensa.
- No se implementó la comunicación backend-a-backend entre Marketing y Ventas (llamada a `POST /api/oportunidades` al convertir un lead). La conversión queda registrada en Marketing pero no crea automáticamente la oportunidad en Ventas.
- El tipo del atributo `Codigo` de `Producto` no fue confirmado entre ambos squads (String vs Int).
