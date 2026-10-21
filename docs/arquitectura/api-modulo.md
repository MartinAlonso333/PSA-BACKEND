# API del módulo

## Alcance del API

El módulo de Marketing expone endpoints para gestionar campañas y leads comerciales.

Cubre el ciclo completo desde el registro de una campaña con sus datos principales y su ciclo de vida (Planificada → Activa → Finalizada), hasta la vinculación de leads a esa campaña, el seguimiento del canal de origen y el avance secuencial del lead hasta su resolución (Nuevo → Contactado → Calificado → Convertido / Perdido).

Cuando un lead resulta convertido, se genera una oportunidad comercial que continuará su gestión dentro del módulo de Ventas.

Quedan fuera del alcance la gestión de tickets de soporte, la carga horaria y el ciclo de vida de proyectos.

Se exponen recursos para gestionar campañas (`/api/campanias`), leads (`/api/leads`), empresas (`/api/empresas`), y consultar catálogos (`/api/productos`, `/api/canales`).

---

## Endpoints

| Método | Path                           | Descripción                                                                       | Estado    |
| ------ | ------------------------------ | --------------------------------------------------------------------------------- | --------- |
| POST   | `/api/campanias`               | Registrar una nueva campaña con fechas, presupuesto, costos y productos asociados | implementado |
| GET    | `/api/campanias`               | Listar campañas con filtro opcional por estado                                    | implementado |
| GET    | `/api/campanias/{id}`          | Obtener el detalle de una campaña                                                 | implementado |
| PUT    | `/api/campanias/{id}`          | Actualizar datos de una campaña (nombre, fechas, presupuesto, costo, productos)   | implementado |
| PATCH  | `/api/campanias/{id}/estado`   | Cambiar el estado de una campaña (Planificada → Activa → Finalizada)              | implementado |
| GET    | `/api/campanias/{id}/metricas` | Obtener métricas derivadas de una campaña                                         | implementado |
| DELETE | `/api/campanias/{id}`          | Eliminar una campaña (solo si no tiene leads asociados)                           | implementado |
| POST   | `/api/leads`                   | Crear un lead asociado a una campaña activa                                       | implementado |
| GET    | `/api/leads`                   | Listar leads con filtros opcionales (`?campaniaId=`, `?canal=`, `?etapa=`)        | implementado |
| GET    | `/api/leads/{id}`              | Obtener el detalle de un lead                                                     | implementado |
| PUT    | `/api/leads/{id}`              | Actualizar datos de un lead (nombre, empresaId, contacto, productos de interés)     | implementado |
| PATCH  | `/api/leads/{id}/canal-origen` | Actualizar el canal de origen de un lead                                          | implementado |
| PATCH  | `/api/leads/{id}/pipeline`     | Avanzar la etapa del lead en el pipeline (Nuevo → Contactado → Calificado → Convertido / Perdido) | implementado |
| PATCH  | `/api/leads/{id}/cierre`       | Registrar el resultado final del lead (Convertido o Perdido)                      | implementado |
| GET    | `/api/leads/reporte/por-canal` | Obtener consolidado de leads por canal de origen                                  | implementado |
| DELETE | `/api/leads/{id}`              | Eliminar un lead (solo si está en NUEVO, CONTACTADO o CALIFICADO)              | implementado |
| GET    | `/api/productos`               | Listar todos los productos disponibles                                            | implementado |
| GET    | `/api/productos/{id}`          | Obtener un producto por ID                                                        | implementado |
| GET    | `/api/canales`                 | Listar todos los canales disponibles                                              | implementado |
| GET    | `/api/canales/{id}`            | Obtener un canal por ID                                                           | implementado |
| POST   | `/api/empresas`                | Registrar una nueva empresa (nombre, razón social, CUIT, dirección)               | implementado |
| GET    | `/api/empresas`                | Listar todas las empresas                                                         | implementado |
| GET    | `/api/empresas/{id}`           | Obtener una empresa por ID                                                        | implementado |
| GET    | `/api/health`                  | Verificar el estado del backend                                                   | implementado |

---

## Contratos principales

| Recurso                              | Request                                                                                         | Response                                                                                                           | Validaciones                                                                                                     |
| ------------------------------------ | ----------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------- |
| `POST /api/campanias`                | `{ nombre, fechaInicio, fechaFin, presupuesto, costo, estado, productos, canales, roi? }`                 | `{ id, nombre, fechaInicio, fechaFin, presupuesto, costo, estado, roi?, productos, canales, fechaCreacion }`                 | `nombre`, `presupuesto`, `costo`, `estado`, `fechaInicio`, `fechaFin`, `productos` y `canales` son obligatorios; `productos` y `canales` deben contener al menos un elemento (IDs) |
| `PATCH /api/campanias/{id}/estado`   | `{ nuevoEstado }`                                                                               | `{ id, estadoAnterior, estadoActual }`                                                                             | Secuencia obligatoria: Planificada → Activa → Finalizada                                                         |
| `GET /api/campanias/{id}/metricas`   | —                                                                                               | `{ CampaniaId, leadsGenerados, leadsConvertidos, conversion, montoGanadoTotal, roi }`                                                      | La campaña debe existir; las métricas son derivadas (conversión = leadsConvertidos / leadsGenerados * 100); ROI = (montoGanadoTotal - costo) / costo; montoGanadoTotal se obtiene consultando el módulo de Ventas |
| `POST /api/leads`                    | `{ campaniaOrigenId, empresaId?, nombre, canalOrigen, contacto, productosInteres }`               | `{ id, campaniaOrigenId, campaniaNombre, empresa?, nombre, canalOrigen, etapa, resultadoCierre?, motivoPerdida?, contacto, productosInteres, fechaCreacion }` | La campaña debe existir y estar activa; `campaniaOrigenId`, `nombre`, `canalOrigen`, `contacto.email` y `productosInteres` son obligatorios; `empresaId` es opcional |
| `PATCH /api/leads/{id}/canal-origen` | `{ canalOrigen }`                                                                               | `{ id, canalOrigenAnterior, canalOrigenActual, mensaje? }`                                                        | `canalOrigen` es obligatorio; si el canal es el mismo que el actual, se devuelve `mensaje` indicándolo            |
| `PATCH /api/leads/{id}/pipeline`     | `{ etapaSiguiente }`                                                                           | `{ id, etapaAnterior, etapaActual }`                                                                             | Transiciones válidas: Nuevo → Contactado\|Perdido, Contactado → Calificado\|Perdido, Calificado → Convertido     |
| `PATCH /api/leads/{id}/cierre`       | `{ resultado, motivo? }`                                                                        | `{ id, resultado, motivo, campaniaVinculada }`                                                                     | `resultado` obligatorio (GANADO o PERDIDO); si es PERDIDO requiere motivo; si es GANADO la etapa pasa a CONVERTIDO (pendiente integración con módulo de Ventas) |
| `GET /api/leads/reporte/por-canal`   | Query param opcional: `campaniaId`                                                               | `[{ canal, cantidad }]`                                                                                            | Agrupa leads por canal; devuelve lista vacía si no hay registros                                                 |
| `DELETE /api/campanias/{id}`         | —                                                                                               | `204 No Content`                                                                                                   | La campaña debe existir; no debe tener leads asociados                                                             |
| `DELETE /api/leads/{id}`             | —                                                                                               | `204 No Content`                                                                                                   | El lead debe existir; `etapa` debe ser NUEVO, CONTACTADO o CALIFICADO                                                  |
| `PUT /api/campanias/{id}`            | `{ nombre?, fechaInicio?, fechaFin?, presupuesto?, costo?, roi?, productos?, canales? }`          | `{ id, nombre, fechaInicio, fechaFin, presupuesto, costo, estado, productos, canales, fechaCreacion }`             | La campaña debe existir; los campos son opcionales; `productos` y `canales` deben ser listas de IDs válidos |
| `PUT /api/leads/{id}`                | `{ nombre?, empresaId?, campaniaOrigenId?, canalOrigen?, contacto?, productosInteres? }`                                            | `{ id, campaniaOrigenId, empresa, nombre, canalOrigen, etapa, contacto, productosInteres, fechaCreacion }`          | El lead debe existir; los campos son opcionales (solo se actualizan los provistos); si se incluye `contacto`, al menos `email` debe ser válido |
| `GET /api/productos`                 | —                                                                                                | `[{ id, nombre, codigo }]`                                                                                         | —                                                                                                                  |
| `GET /api/productos/{id}`            | —                                                                                                | `{ id, nombre, codigo }`                                                                                           | El producto debe existir                                                                                          |
| `GET /api/canales`                   | —                                                                                                | `[{ id, nombre, tipo }]`                                                                                           | —                                                                                                                  |
| `GET /api/canales/{id}`              | —                                                                                                | `{ id, nombre, tipo }`                                                                                             | El canal debe existir                                                                                             |
| `POST /api/empresas`                 | `{ nombre, razonSocial?, cuit?, direccion? }`                                                   | `{ id, nombre, razonSocial, cuit, direccion }`                                                                      | `nombre` es obligatorio; si se provee `cuit` y ya existe, se devuelve la empresa existente en lugar de crear una nueva |
| `GET /api/empresas`                  | —                                                                                                | `[{ id, nombre, razonSocial, cuit, direccion }]`                                                                   | —                                                                                                                  |
| `GET /api/empresas/{id}`             | —                                                                                                | `{ id, nombre, razonSocial, cuit, direccion }`                                                                     | La empresa debe existir                                                                                           |

---

## Errores y validaciones

Todos los errores retornan un cuerpo JSON con el formato `{ error, timestamp }`.

* **400 Bad Request — campo obligatorio ausente**: falta información requerida para crear campañas o leads.
* **400 Bad Request — transición inválida**: no se permiten saltos ni retrocesos en los estados de campañas (Planificada → Activa → Finalizada).
* **400 Bad Request — cierre sin resultado**: el resultado final es obligatorio (GANADO o PERDIDO).
* **400 Bad Request — pérdida sin motivo**: un lead perdido debe registrar un motivo.
* **400 Bad Request — valor inválido**: el estado, la etapa, el resultado o el canal proporcionado no son válidos.
* **404 Not Found — recurso inexistente**: la campaña, lead, empresa, producto o canal no existen.
* **422 Unprocessable Entity — campaña inactiva**: no pueden registrarse leads sobre campañas que no estén activas.

---

## Decisiones abiertas

* Definir si se mantendrá historial de cambios de canal de origen para auditoría.
* Definir restricciones de acceso al reporte de leads por canal.
* Confirmar el contrato de integración entre Marketing y Ventas para la generación automática de oportunidades.

## Decisiones resueltas

* **Fórmula de ROI**: `ROI = (montoGanadoTotal - costo) / costo`, donde `montoGanadoTotal` se obtiene sumando los `montoGanado` de cada lead convertido consultando al módulo de Ventas (`GET /api/oportunidades/lead/{leadId}`). El ROI se calcula al invocar `GET /api/campanias/{id}/metricas` y se persiste en la campaña.
