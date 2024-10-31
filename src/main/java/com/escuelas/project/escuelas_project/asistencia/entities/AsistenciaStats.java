package com.escuelas.project.escuelas_project.asistencia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsistenciaStats{
    
    private AsistioEnum asistio;
    private int total;

    public AsistenciaStats(AsistioEnum asistio) {
        this.asistio = asistio;
        this.total = 0;
    }

    public void updateTotal() {
        this.total++;
    }
}
