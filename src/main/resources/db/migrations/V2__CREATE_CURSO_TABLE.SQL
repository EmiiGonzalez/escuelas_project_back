CREATE TABLE cursos (
    id_curso BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    materia VARCHAR(255) NOT NULL,
    fecha_de_curso DATE,
    id_escuela BIGINT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_escuela) REFERENCES escuelas(id_escuela)
);
