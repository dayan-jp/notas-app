CREATE TABLE usuario (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(50) UNIQUE NOT NULL,
    password    VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE nota (
    id          BIGSERIAL PRIMARY KEY,
    usuario_id  BIGINT NOT NULL REFERENCES usuario(id),
    titulo      VARCHAR(100) NOT NULL,
    contenido   TEXT,
    fecha       DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at  TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_nota_usuario ON nota(usuario_id);

-- El usuario "admin" se crea automáticamente al arrancar el backend
-- (ver DataSeeder.java), así el hash BCrypt siempre se genera en runtime
-- y no se escribe a mano en SQL.
