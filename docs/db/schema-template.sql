-- Derivar este archivo a `docs/db/schema.sql`.
-- Mantener una versión legible del esquema actual del módulo.

CREATE TABLE ejemplo_recurso (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
