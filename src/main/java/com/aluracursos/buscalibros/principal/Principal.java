package com.aluracursos.buscalibros.principal;

import com.aluracursos.buscalibros.model.Datos;
import com.aluracursos.buscalibros.model.DatosDeLibros;
import com.aluracursos.buscalibros.service.ConsumoAPI;
import com.aluracursos.buscalibros.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    public void muestraElMenu(){

        var json = consumoAPI.obtenerDatos("https://gutendex.com/books/");
        System.out.println(json);

        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        //Top 10 de libros
        System.out.println("Top 10 de libros");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosDeLibros::numeroDeDescargas).reversed())
                .map(datosDeLibros -> datosDeLibros.titulo().toUpperCase())
                .limit(10)
                .forEach(System.out::println);

        //Busca libro por titulo o parte de el
        System.out.println("Escriba el titulo del libro que desea buscar: ");
        var parteDelTitulo = teclado.nextLine();

        json = consumoAPI.obtenerDatos("https://gutendex.com/books/?search=" + parteDelTitulo.replace(" ","+"));
        //json = consumoAPI.obtenerDatos("https://gutendex.com/books?search=quijo");
        System.out.println(json);

        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosDeLibros> libroBuscado = datosBusqueda.resultados().stream()

                .filter(l-> l.titulo().toUpperCase().contains(parteDelTitulo.toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado!");
            System.out.println("Los datos son: " + libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado!");
        }

        //Trabajando con estadisticas
//        DoubleSummaryStatistics est = datos.resultados().stream()
//                .filter(datosDeLibros -> datosDeLibros.numeroDeDescargas()>0)
//                .collect(Collectors.summarizingDouble(DatosDeLibros::numeroDeDescargas));
//        System.out.println("Cantidad media de descargas: " + est.getAverage());
//        System.out.println("Cantidad maxima de descargas: " +est.getMax());
//        System.out.println("Cantidad minima de descargas: " +est.getMin());
//        System.out.println("Cantidad de registros: " +est.getCount());
    }
}
