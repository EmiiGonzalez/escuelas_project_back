package com.escuelas.project.escuelas_project.escuela.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "escuelas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Escuela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_escuela;
    private String nombre;
    private Boolean activo;

    public Escuela(String nombre) {
        this.nombre = nombre;
        this.activo = true;
    }

    public void update(EscuelaDtoUpdate escuela) {
        if (escuela.nombre() != null && !escuela.nombre().trim().isEmpty()) {
            this.setNombre(escuela.nombre());
        }
    }

    public void disable() {
        this.activo = false;
    }

}
