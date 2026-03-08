-- ============================================================
-- Initial Schema Migration
-- Escuelas Project - PostgreSQL
-- ============================================================

-- ============================================================
-- Table: escuelas
-- ============================================================
CREATE TABLE IF NOT EXISTS escuelas (
    id UUID PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

COMMENT ON TABLE escuelas IS 'Escuelas donde se dictan los cursos';
COMMENT ON COLUMN escuelas.id IS 'UUID identificador unico de la escuela';
COMMENT ON COLUMN escuelas.nombre IS 'Nombre de la escuela';
COMMENT ON COLUMN escuelas.activo IS 'Estado de la escuela (activo/inactivo)';

-- ============================================================
-- Table: cursos
-- ============================================================
CREATE TABLE IF NOT EXISTS cursos (
    id UUID PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    materia VARCHAR(255) NOT NULL,
    fecha_de_curso DATE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    escuela_id UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_cursos_escuela FOREIGN KEY (escuela_id) 
        REFERENCES escuelas(id) ON DELETE CASCADE
);

COMMENT ON TABLE cursos IS 'Cursos dictados en las escuelas';
COMMENT ON COLUMN cursos.id IS 'UUID identificador unico del curso';
COMMENT ON COLUMN cursos.nombre IS 'Nombre del curso';
COMMENT ON COLUMN cursos.materia IS 'Materia del curso';
COMMENT ON COLUMN cursos.fecha_de_curso IS 'Fecha de inicio del curso';
COMMENT ON COLUMN cursos.activo IS 'Estado del curso (activo/inactivo)';
COMMENT ON COLUMN cursos.escuela_id IS 'Referencia a la escuela';

-- ============================================================
-- Table: alumnos
-- ============================================================
CREATE TABLE IF NOT EXISTS alumnos (
    id UUID PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    curso_id UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_alumnos_curso FOREIGN KEY (curso_id) 
        REFERENCES cursos(id) ON DELETE CASCADE
);

COMMENT ON TABLE alumnos IS 'Alumnos inscriptos en los cursos';
COMMENT ON COLUMN alumnos.id IS 'UUID identificador unico del alumno';
COMMENT ON COLUMN alumnos.nombre IS 'Nombre del alumno';
COMMENT ON COLUMN alumnos.telefono IS 'Telefono de contacto del alumno';
COMMENT ON COLUMN alumnos.activo IS 'Estado del alumno (activo/inactivo)';
COMMENT ON COLUMN alumnos.curso_id IS 'Referencia al curso';

-- ============================================================
-- Table: clases
-- ============================================================
CREATE TABLE IF NOT EXISTS clases (
    id UUID PRIMARY KEY,
    fecha_de_clase DATE NOT NULL,
    contenido TEXT,
    numero_de_clase INTEGER NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    curso_id UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_clases_curso FOREIGN KEY (curso_id) 
        REFERENCES cursos(id) ON DELETE CASCADE
);

COMMENT ON TABLE clases IS 'Clases programadas para cada curso';
COMMENT ON COLUMN clases.id IS 'UUID identificador unico de la clase';
COMMENT ON COLUMN clases.fecha_de_clase IS 'Fecha en que se dicta la clase';
COMMENT ON COLUMN clases.contenido IS 'Contenido tematico de la clase';
COMMENT ON COLUMN clases.numero_de_clase IS 'Numero ordenado de la clase';
COMMENT ON COLUMN clases.activo IS 'Estado de la clase (activo/inactivo)';
COMMENT ON COLUMN clases.curso_id IS 'Referencia al curso';

-- ============================================================
-- Table: asistencias
-- ============================================================
CREATE TABLE IF NOT EXISTS asistencias (
    id UUID PRIMARY KEY,
    asistio VARCHAR(20) NOT NULL,
    clase_id UUID NOT NULL,
    alumno_id UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_asistencias_clase FOREIGN KEY (clase_id) 
        REFERENCES clases(id) ON DELETE CASCADE,
    CONSTRAINT fk_asistencias_alumno FOREIGN KEY (alumno_id) 
        REFERENCES alumnos(id) ON DELETE CASCADE,
    CONSTRAINT uq_asistencias_alumno_clase UNIQUE (alumno_id, clase_id)
);

COMMENT ON TABLE asistencias IS 'Registro de asistencia de alumnos a clases';
COMMENT ON COLUMN asistencias.id IS 'UUID identificador unico de la asistencia';
COMMENT ON COLUMN asistencias.asistio IS 'Estado de asistencia: PRESENTE, AUSENTE, JUSTIFICADO, NOENLISTADO';
COMMENT ON COLUMN asistencias.clase_id IS 'Referencia a la clase';
COMMENT ON COLUMN asistencias.alumno_id IS 'Referencia al alumno';
COMMENT ON CONSTRAINT uq_asistencias_alumno_clase ON asistencias IS 'Un alumno solo puede tener un registro de asistencia por clase';

-- ============================================================
-- Indexes for better performance
-- ============================================================
CREATE INDEX IF NOT EXISTS idx_cursos_escuela ON cursos(escuela_id);
CREATE INDEX IF NOT EXISTS idx_cursos_activo ON cursos(activo);
CREATE INDEX IF NOT EXISTS idx_alumnos_curso ON alumnos(curso_id);
CREATE INDEX IF NOT EXISTS idx_alumnos_activo ON alumnos(activo);
CREATE INDEX IF NOT EXISTS idx_clases_curso ON clases(curso_id);
CREATE INDEX IF NOT EXISTS idx_clases_fecha ON clases(fecha_de_clase);
CREATE INDEX IF NOT EXISTS idx_clases_activo ON clases(activo);
CREATE INDEX IF NOT EXISTS idx_asistencias_clase ON asistencias(clase_id);
CREATE INDEX IF NOT EXISTS idx_asistencias_alumno ON asistencias(alumno_id);
CREATE INDEX IF NOT EXISTS idx_escuelas_activo ON escuelas(activo);

