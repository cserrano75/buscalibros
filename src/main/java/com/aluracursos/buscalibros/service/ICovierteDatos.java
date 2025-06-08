package com.aluracursos.buscalibros.service;

public interface ICovierteDatos {
    <T> T obtenerDatos(String json, Class<T>clase);
}
