package com.aluracursos.buscalibros.principal;

import com.aluracursos.buscalibros.model.Datos;
import com.aluracursos.buscalibros.model.DatosDeLibros;
import com.aluracursos.buscalibros.service.CatalogoService;
import com.aluracursos.buscalibros.service.ConsumoAPI;
import com.aluracursos.buscalibros.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {
    private final CatalogoService catalogoService;
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    @Autowired

    //private Scanner teclado = new Scanner(System.in);

    // Inyección por constructor recomendada
    public Principal(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    public void mostrarMenu() {
        Scanner teclado = new Scanner(System.in);
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                1 - Buscar libro por nombre
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un año
                5 - Listar libros por idioma
                0 - Salir
                """);

            // Leer opción de forma segura
            try {
                opcion = Integer.parseInt(teclado.nextLine());
                System.out.println("Opcion elegida:" + opcion);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
                continue;
            }
//
//            opcion = teclado.nextInt();
//            teclado.nextLine();

            switch (opcion) {
                case 1 -> catalogoService.buscarLibroPorNombre();
                case 2 -> catalogoService.listarLibros();
                case 3 -> catalogoService.listarAutores();
                case 4 -> opcionListarAutoresVivos();
                case 5 -> catalogoService.listarLibrosPorIdioma();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void opcionListarAutoresVivos() {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Ingrese el año (ej. 1888): ");
        String entrada = teclado.nextLine().trim();

        Integer anio = null;
        try {
            anio = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Año inválido. Ingrese un número entero válido.");
            return;
        }

        catalogoService.listarAutoresVivosEnAnio(anio);
    }

    public void muestraElMenu(){

//        var json = consumoAPI.obtenerDatos("https://gutendex.com/books/");
//        System.out.println(json);
//
//        var datos = conversor.obtenerDatos(json, Datos.class);
//        System.out.println(datos);

        //Top 10 de libros
//        System.out.println("Top 10 de libros");
//        datos.resultados().stream()
//                .sorted(Comparator.comparing(DatosDeLibros::numeroDeDescargas).reversed())
//                .map(datosDeLibros -> datosDeLibros.titulo().toUpperCase())
//                .limit(10)
//                .forEach(System.out::println);

        //Busca libro por titulo o parte de el
//        System.out.println("Escriba el titulo del libro que desea buscar: ");
//        var parteDelTitulo = teclado.nextLine();
//
//        json = consumoAPI.obtenerDatos("https://gutendex.com/books/?search=" + parteDelTitulo.replace(" ","+"));
//        //json = consumoAPI.obtenerDatos("https://gutendex.com/books?search=quijo");
//        System.out.println(json);
//
//        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
//
//        Optional<DatosDeLibros> libroBuscado = datosBusqueda.resultados().stream()
//
//                .filter(l-> l.titulo().toUpperCase().contains(parteDelTitulo.toUpperCase()))
//                .findFirst();
//
//        if(libroBuscado.isPresent()){
//            System.out.println("Libro encontrado!");
//            System.out.println("Los datos son: " + libroBuscado.get());
//        }else {
//            System.out.println("Libro no encontrado!");
//        }

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
