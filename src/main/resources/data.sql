-- =============================================================================
-- Módulo Marketing – datos maestros (canales y productos)
-- =============================================================================
-- El resto de las entidades (empresas, campañas, leads, contactos) se crean
-- desde la UI de TRIBU.
-- Nota: los INSERT asumen que las tablas ya fueron creadas por Hibernate
-- (spring.jpa.hibernate.ddl-auto=create) y que las PK auto-incrementales
-- empiezan en 1.

-- Canales -------------------------------------------------------------------
INSERT INTO canal (nombre, tipo) VALUES
    ('Email',     'DIGITAL'),
    ('Teléfono',  'TELEFONICO'),
    ('LinkedIn',  'RED_SOCIAL'),
    ('Web',       'DIGITAL'),
    ('Evento presencial', 'PRESENCIAL');

-- Productos -----------------------------------------------------------------
INSERT INTO producto (nombre, codigo) VALUES
    ('CRM Básico',      'CRM-001'),
    ('CRM Premium',     'CRM-002'),
    ('Marketing Pro',   'MKT-001'),
    ('Consultoría CRM', 'CON-001');
