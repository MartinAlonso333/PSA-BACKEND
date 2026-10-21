# D1.3 - Diagrama de Código

## Motivo del diagrama

Mostrar las clases principales del backend del módulo de Marketing (controllers, services, repositorios y entidades) y sus relaciones de dependencia.

## Recorte aplicado

- Se incluyen solo las clases del paquete `ar.edu.fiuba.is1.tpg` involucradas en los flujos de Campañas y Leads.
- Se omiten `PingController`, `PingService`, `PingRepository` y `DocsController` por no pertenecer al dominio de Marketing.
- Los DTOs de request/response no se representan como clases separadas para no saturar el diagrama; se referencian como notas.

## Diagrama

```mermaid
classDiagram
    class CampaniaController {
        +POST /api/campanias
        +GET /api/campanias
        +GET /api/campanias/{id}
        +PUT /api/campanias/{id}
        +PATCH /api/campanias/{id}/estado
        +GET /api/campanias/{id}/metricas
    }
    class LeadController {
        +POST /api/leads
        +GET /api/leads
        +GET /api/leads/{id}
        +PUT /api/leads/{id}
        +PATCH /api/leads/{id}/pipeline
        +PATCH /api/leads/{id}/cierre
        +PATCH /api/leads/{id}/canal-origen
        +GET /api/leads/reporte/por-canal
    }
    class ProductoController {
        +GET /api/productos
        +GET /api/productos/{id}
    }
    class CanalController {
        +GET /api/canales
        +GET /api/canales/{id}
    }
    class EmpresaController {
        +POST /api/empresas
        +GET /api/empresas
        +GET /api/empresas/{id}
    }
    class CampaniaService
    class LeadService
    class VentasService
    class EmpresaService
    class CampaniaRepository
    class LeadComercialRepository
    class ProductoRepository
    class ContactoRepository
    class CanalRepository
    class EmpresaRepository
    class GlobalExceptionHandler

    CampaniaController --> CampaniaService : delega
    LeadController --> LeadService : delega
    ProductoController --> ProductoService : delega
    CanalController --> CanalService : delega
    EmpresaController --> EmpresaService : delega
    EmpresaService --> EmpresaRepository : persiste
    CampaniaService --> CampaniaRepository : persiste
    CampaniaService --> ProductoRepository : consulta
    CampaniaService --> LeadComercialRepository : métricas
    CampaniaService --> VentasService : ROI
    LeadService --> LeadComercialRepository : persiste
    LeadService --> ContactoRepository : persiste
    LeadService --> ProductoRepository : consulta
    LeadService --> CanalRepository : resuelve canal
    LeadService --> EmpresaRepository : resuelve empresa
    LeadService --> CampaniaService : valida campaña activa
    GlobalExceptionHandler ..> CampaniaController : intercepta
    GlobalExceptionHandler ..> LeadController : intercepta
    GlobalExceptionHandler ..> ProductoController : intercepta
    GlobalExceptionHandler ..> CanalController : intercepta
    GlobalExceptionHandler ..> EmpresaController : intercepta
```

## Clases concretas del módulo Marketing

### Controladores

| Clase | Ruta base | Métodos públicos |
|---|---|---|
| `CampaniaController` | `/api/campanias` | `registrar` (POST), `listar` (GET), `obtener` (GET), `actualizar` (PUT), `avanzarEstado` (PATCH), `obtenerMetricas` (GET) |
| `LeadController` | `/api/leads` | `registrar` (POST), `listar` (GET), `obtener` (GET), `actualizar` (PUT), `actualizarCanalOrigen` (PATCH), `avanzarPipeline` (PATCH), `cerrar` (PATCH), `reportePorCanal` (GET) |
| `ProductoController` | `/api/productos` | `listar` (GET), `obtener` (GET) |
| `CanalController` | `/api/canales` | `listar` (GET), `obtener` (GET) |
| `EmpresaController` | `/api/empresas` | `listar` (GET), `obtener` (GET), `crear` (POST) |

### Servicios

| Clase | Dependencias |
|---|---|
| `CampaniaService` | `CampaniaRepository`, `ProductoRepository`, `LeadComercialRepository`, `VentasService` |
| `LeadService` | `LeadComercialRepository`, `ContactoRepository`, `ProductoRepository`, `CanalRepository`, `EmpresaRepository`, `CampaniaService` |
| `ProductoService` | `ProductoRepository` |
| `CanalService` | `CanalRepository` |
| `EmpresaService` | `EmpresaRepository` |
| `VentasService` | `RestTemplate` (llamada HTTP a `GET /api/oportunidades/lead/{leadId}` del Backend de Ventas) |

### Repositorios

| Clase | Extiende | Métodos adicionales |
|---|---|---|
| `CampaniaRepository` | `JpaRepository<Campania, Long>` | `findByEstado`, `findByNombre` |
| `LeadComercialRepository` | `JpaRepository<LeadComercial, Long>` | `findByCampaniaId`, `findByCanal`, `findByEtapa`, `findByEmpresa`, `contarPorCanal` (JPQL), `contarPorCanalYCampania` (JPQL) |
| `EmpresaRepository` | `JpaRepository<Empresa, Long>` | `findByNombre`, `findByCuit` |
| `ProductoRepository` | `JpaRepository<Producto, Long>` | `findByNombre`, `findByCodigo` |
| `ContactoRepository` | `JpaRepository<Contacto, Long>` | — |
| `CanalRepository` | `JpaRepository<Canal, Long>` | `findByNombre` |

### Entidades

| Clase | `@Table` | Columnas clave |
|---|---|---|
| `Campania` | `campania` | id, nombre, fecha_inicio, fecha_fin, presupuesto, costo, roi (calculado vía `VentasService`), estado (enum `EstadoCampania`), creado_en, actualizado_en |
| `Canal` | `canal` | id, nombre (unique), tipo |
| `LeadComercial` | `lead_comercial` | id, nombre, empresa_id (FK), etapa (enum `EtapaPipeline`), canal_id (FK), canal_anterior_id (FK), campania_id (FK), resultado_cierre (enum `ResultadoCierre`), motivo_perdida, creado_en, actualizado_en |
| `Empresa` | `empresa` | id, nombre, razon_social, cuit, direccion |
| `Producto` | `producto` | id, nombre, codigo (unique) |
| `Contacto` | `contacto` | id, lead_id (FK, unique), email, telefono |

### Tablas intermedias (JoinTable)

| Tabla | Entidad origen | Entidad destino |
|---|---|---|
| `campania_producto` | `Campania` (campania_id) | `Producto` (producto_id) |
| `campania_canal` | `Campania` (campania_id) | `Canal` (canal_id) |
| `lead_producto` | `LeadComercial` (lead_id) | `Producto` (producto_id) |

### Excepciones de dominio

- `CampaniaInactivaException` — operación sobre campaña no activa
- `RecursoNoEncontradoException` — entidad no encontrada por ID
- `TransicionInvalidaException` — salto de estado/etapa no permitido

### DTOs

| Clase | Uso |
|---|---|
| `CampaniaRequest` | Request body para POST `/api/campanias` |
| `CampaniaResponse` | Respuesta de campaña (incluye lista de productos) |
| `CampaniaUpdateRequest` | Request body para PUT `/api/campanias/{id}` (todos los campos opcionales) |
| `LeadRequest` | Request body para POST `/api/leads` (incluye `ContactoRequest` anidado) |
| `LeadResponse` | Respuesta de lead (incluye datos de contacto y productos) |
| `LeadUpdateRequest` | Request body para PUT `/api/leads/{id}` (todos los campos opcionales) |
| `AvancePipelineRequest` | Request body para PATCH `.../pipeline` (campo `etapaSiguiente`) |
| `CierreLeadRequest` | Request body para PATCH `.../cierre` (campos `resultado`, `motivo`) |
| `CanalOrigenRequest` | Request body para PATCH `.../canal-origen` (campo `canalOrigen`) |
| `ContactoRequest` | Anidado en `LeadRequest` / `LeadResponse` / `LeadUpdateRequest` (email, telefono, direccion) |
| `EmpresaRequest` | Request body para POST `/api/empresas` (nombre, razonSocial, cuit, direccion) |
| `EmpresaResponse` | Respuesta de empresa (id, nombre, razonSocial, cuit, direccion); también anidado en `LeadResponse` |
| `ProductoResponse` | Respuesta de producto (id, nombre, codigo) |
| `CanalResponse` | Respuesta de canal (id, nombre, tipo) |

## Trazabilidad funcional ↔ código ↔ pruebas

| HU | Historia | Endpoint(s) | Controller | Service | Archivo `.feature` |
|---|---|---|---|---|---|---|
| HU-06 | Registro y métricas de campañas | `POST /api/campanias`, `GET /api/campanias/{id}/metricas`, `PUT /api/campanias/{id}`, `DELETE /api/campanias/{id}` | `CampaniaController` | `CampaniaService` | `src/test/resources/features/HU-06.feature` |
| HU-07 | Gestión del pipeline de leads | `POST /api/leads`, `PATCH /api/leads/{id}/pipeline`, `PATCH /api/leads/{id}/cierre`, `GET /api/leads`, `GET /api/leads/{id}` | `LeadController` | `LeadService` | `src/test/resources/features/HU-07.feature` |
| HU-22 | Registro del canal de origen y actualización de datos | `POST /api/leads`, `PATCH /api/leads/{id}/canal-origen`, `GET /api/leads/reporte/por-canal`, `PUT /api/leads/{id}` | `LeadController` | `LeadService` | `src/test/resources/features/HU-22.feature` |
| HU-24 | Asociación de leads a campañas | `POST /api/leads` | `LeadController` | `LeadService` | `src/test/resources/features/HU-24.feature` |
| HU-25 | Consulta de catálogos (productos y canales) | `GET /api/productos`, `GET /api/productos/{id}`, `GET /api/canales`, `GET /api/canales/{id}` | `ProductoController`, `CanalController` | `ProductoService`, `CanalService` | `src/test/resources/features/HU-25.feature` |
| HU-26 | Gestión de empresas | `GET /api/empresas`, `GET /api/empresas/{id}`, `POST /api/empresas` | `EmpresaController` | `EmpresaService` | `src/test/resources/features/HU-26.feature` |

## Lectura recomendada

- `docs/arquitectura/c4-componentes.md` — **D1.2** descripción detallada de cada componente y sus interacciones.
- `docs/db/modelo-er.md` — **D1.4** modelo entidad-relación de las tablas subyacentes.
- `docs/db/schema.sql` — **D1.4** DDL completo con nombres de tablas y columnas alineados con las entidades JPA.
