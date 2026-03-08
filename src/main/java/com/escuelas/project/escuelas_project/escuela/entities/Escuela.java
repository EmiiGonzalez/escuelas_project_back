package com.escuelas.project.escuelas_project.escuela.entities;

import com.escuelas.project.escuelas_project.common.entities.BaseEntity;
import com.escuelas.project.escuelas_project.escuela.dtos.EscuelaDtoUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "escuelas")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Escuela extends BaseEntity {
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

    public void enable() {
        this.activo = true;
    }
}
