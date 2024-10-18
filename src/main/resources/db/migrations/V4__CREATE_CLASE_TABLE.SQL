CREATE TABLE clases (
    id_clase BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_de_clase DATE NOT NULL,
    contenido TEXT,
    numero_de_clase INT,
    id_curso BIGINT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso) ON DELETE CASCADE  
);