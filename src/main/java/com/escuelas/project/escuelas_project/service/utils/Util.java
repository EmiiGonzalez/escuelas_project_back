package com.escuelas.project.escuelas_project.service.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {
    
    public static LocalDate parsearFecha(String fecha) {
        LocalDate fechaParseada = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return fechaParseada;
    }
}
