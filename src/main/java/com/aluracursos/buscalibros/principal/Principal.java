package com.aluracursos.buscalibros.principal;

import com.aluracursos.buscalibros.model.Datos;
import com.aluracursos.buscalibros.model.DatosDeLibros;
import com.aluracursos.buscalibros.service.ConsumoAPI;
import com.aluracursos.buscalibros.service.ConvierteDatos;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu(){

        var json = consumoAPI.obtenerDatos("https://gutendex.com/books/");
        System.out.println(json);

        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

    }
}
