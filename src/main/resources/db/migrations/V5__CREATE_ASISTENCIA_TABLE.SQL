CREATE TABLE asistencias (
    id_asistencia BIGINT AUTO_INCREMENT PRIMARY KEY,
    asistio BOOLEAN,
    id_clase BIGINT,
    id_alumno BIGINT,
    FOREIGN KEY (id_clase) REFERENCES clases(id_clase),
    FOREIGN KEY (id_alumno) REFERENCES alumnos(id_alumno)
    UNIQUE (id_clase, id_alumno) --Restriccion unica para la clase y el alumno
);
