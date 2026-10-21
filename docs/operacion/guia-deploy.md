# Guía de deploy

- [Enlace a nuestro backend en Render](https://two026-1c-tpg-squad-1-tv35.onrender.com)
- [Swagger UI](https://two026-1c-tpg-squad-1-tv35.onrender.com/swagger-ui.html)
- [Health Check](https://two026-1c-tpg-squad-1-tv35.onrender.com/api/health)

## Stack

- **Backend**: Spring Boot 3.3.5 + Java 21 + PostgreSQL (Render Web Service, rama `iteracion-06`)

## URLs actuales

| Componente | URL | Rama |
|---|---|---|
| Backend Marketing | `https://two026-1c-tpg-squad-1-tv35.onrender.com` | `iteracion-06` |
| Swagger UI | `https://two026-1c-tpg-squad-1-tv35.onrender.com/swagger-ui.html` | `iteracion-06` |
| OpenAPI JSON | `https://two026-1c-tpg-squad-1-tv35.onrender.com/v3/api-docs` | `iteracion-06` |
| Health Check | `https://two026-1c-tpg-squad-1-tv35.onrender.com/api/health` | `iteracion-06` |

## Deploy automático

Cada push a la rama correspondiente dispara un deploy automático en Render.

| Repo | Rama | Servicio Render |
|---|---|---|
| TPG (backend marketing) | `iteracion-06` | Web Service |

## Paso a paso del deploy

### 1. Crear cuenta en Render

Crear una cuenta en [render.com](https://render.com) vinculada con GitHub y otorgar acceso al repositorio `2026-1c-tpg-squad-1`.

### 2. Crear el Web Service

Desde el dashboard de Render:

1. **New + → Web Service**
2. Conectar el repositorio `2026-1c-tpg-squad-1`
3. Configurar:
   - **Name**: `two026-1c-tpg-squad-1`
   - **Branch**: `iteracion-06`
   - **Runtime**: `Docker`
   - **Dockerfile Path**: `./Dockerfile`
   - **Instance Type**: `Free`
4. Hacer clic en **Create Web Service**

Render construye la imagen Docker y despliega automáticamente.

### 3. Crear la base de datos PostgreSQL

1. **New + → PostgreSQL**
2. Configurar:
   - **Name**: `tpg-squad-1-db`
   - **PostgreSQL Version**: `16`
   - **Instance Type**: `Free`
3. Una vez creada, copiar la **Internal Database String** (desde el dashboard de la DB).
4. Esa string se usará como valor de `DB_URL` en las variables de entorno del Web Service.

### 4. Configurar environment variables

Ir al dashboard del Web Service → **Environment** → agregar:

| Variable | Valor |
|---|---|
| `DB_URL` | Internal Database String de la PostgreSQL (JDBC) |
| `DB_USER` | Usuario de la DB (lo entrega Render) |
| `DB_PASSWORD` | Password de la DB (lo entrega Render) |
| `JPA_DDL_AUTO` | `update` |
| `JPA_SHOW_SQL` | `false` |
| `JPA_FORMAT_SQL` | `false` |
| `SQL_INIT_MODE` | `never` |
| `CORS_ALLOWED_ORIGINS` | `https://two026-1c-tpg-tribu-b.onrender.com` |

Luego hacer clic en **Save Changes**. El servicio se redeploya automáticamente.

### 5. Verificar el deploy

Una vez que el deploy finalice (estado `Live`):

- Health check: `GET https://two026-1c-tpg-squad-1-tv35.onrender.com/api/health`
- Swagger UI: `https://two026-1c-tpg-squad-1-tv35.onrender.com/swagger-ui.html`

### 6. Deploy de nuevas versiones

Cada `git push` a `iteracion-06` dispara un deploy automático. Para forzar un redeploy manual:

```
git commit --allow-empty -m "chore: redeploy"
git push origin iteracion-06
```

O desde el dashboard de Render: **Manual Deploy → Deploy latest commit**.

## Env vars en Render

| Variable | Descripción |
|---|---|
| `DB_URL` | JDBC URL de PostgreSQL |
| `DB_USER` | Usuario de DB |
| `DB_PASSWORD` | Password de DB |
| `JPA_DDL_AUTO` | `update` (no perder datos al redeployar) |
| `JPA_SHOW_SQL` | `false` en producción |
| `JPA_FORMAT_SQL` | `false` en producción |
| `SQL_INIT_MODE` | `never` (los seeds ya se cargaron) |
| `CORS_ALLOWED_ORIGINS` | Origins permitidos, separados por coma |

## Integración con el frontend (TRIBU)

- URL del frontend: `https://two026-1c-tpg-tribu-b.onrender.com`
- Rama oficial del frontend: `integracion-02`
- CORS: configurado vía la variable `CORS_ALLOWED_ORIGINS` en Render (separar múltiples orígenes con coma)
- Health endpoint: `GET /api/health` (implementado en `PingController`)

**Acciones realizadas:**

1. ✅ CORS parametrizado vía `app.cors.allowed-origins` en `application.properties` y `AppConfig.java`
2. ✅ Puerto dinámico tomado de la variable `$PORT` de Render en el `Dockerfile`
3. ✅ Dockerfile multi-stage configurado para build y runtime con `eclipse-temurin:21`

## Consideraciones

- El plan free de Render **duerme** el Web Service tras 15 min sin uso. El primer request tarda ~30s en despertar.
- PostgreSQL free **expira a los 90 días**. Verificar antes de la defensa.
- El puerto se toma dinámicamente de `$PORT` según la configuración de Render (`Dockerfile` línea 30).
- Swagger UI disponible en `/swagger-ui.html` y también en `/docs`.
