CREATE TABLE clases (
    id_clase BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_de_clase DATE,
    contenido TEXT,
    numero_de_clase INT,
    id_curso BIGINT NOT NULL,
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)
);
