package com.aluracursos.buscalibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year")String fechaDeNacimiento
) {
    public DatosAutor {
        if (nombre == null) {
            nombre = "Desconocido";
        }
        if (fechaDeNacimiento == null) {
            fechaDeNacimiento = "Fecha desconocida";
        }
    }
}
