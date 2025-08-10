package com.aluracursos.buscalibros.service;

import com.aluracursos.buscalibros.model.*;
import com.aluracursos.buscalibros.repository.AutorRepository;
import com.aluracursos.buscalibros.repository.LibroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CatalogoService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    private final AutorRepository autorRepository;

    public CatalogoService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void buscarLibroPorNombre() {
        System.out.println("Ingrese el título o parte del título: ");
        String parteDelTitulo = teclado.nextLine();

        String url = "https://gutendex.com/books/?search=" + parteDelTitulo.replace(" ", "+");
        String json = consumoAPI.obtenerDatos(url);

        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosDeLibros> libroApi = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(parteDelTitulo.toUpperCase()))
                .findFirst();

        if (libroApi.isEmpty()) {
            System.out.println("El libro no fue encontrado");
            return;
        }

        DatosDeLibros libroEncontrado = libroApi.get();

        // Evitar duplicados
        if (libroRepository.findByTituloIgnoreCase(libroEncontrado.titulo()).isPresent()) {
            System.out.println("No puede insertar el mismo libro más de una vez");
            return;
        }

        // Guardar autor si no existe
        DatosAutor datosAutor = libroEncontrado.autor().get(0);
        Autor autor = autorRepository.findAll().stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(datosAutor.nombre()))
                .findFirst()
                .orElseGet(() -> {
                    Autor nuevo = new Autor();
                    nuevo.setNombre(datosAutor.nombre());
                    nuevo.setFechaNacimiento(parseEntero(datosAutor.fechaDeNacimiento()));
                    nuevo.setFechaFallecimiento(null);
                    return autorRepository.save(nuevo);
                });

        // Guardar libro
        Libro libro = new Libro();
        libro.setTitulo(libroEncontrado.titulo());
        libro.setIdioma(libroEncontrado.idiomas().get(0));
        libro.setNumeroDeDescargas(libroEncontrado.numeroDeDescargas());
        libro.setAutor(autor);

        libroRepository.save(libro);
        System.out.println("Libro guardado exitosamente.");
    }

    private Integer parseEntero(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void listarLibros() {
        libroRepository.findAll().forEach(l -> {
            System.out.println("LIBRO");
            System.out.println("Título: " + l.getTitulo());
            System.out.println("Autor: " + l.getAutor().getNombre());
            System.out.println("Idioma: " + l.getIdioma());
            System.out.println("Descargas: " + l.getNumeroDeDescargas());
            System.out.println("--------------------");
        });
    }

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAllAutoresConLibros();
        for (Autor autor : autores) {
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getFechaNacimiento());
            System.out.println("Fallecimiento: " + autor.getFechaFallecimiento());
            System.out.println("Libros:");
            autor.getLibros().forEach(libro -> System.out.println("  " + libro.getTitulo()));
            System.out.println("---------------------");
        }
    }

    @Transactional(readOnly = true)
    public void listarAutoresVivosEnAnio(Integer anio) {
        if (anio == null) {
            System.out.println("Año inválido.");
            return;
        }

        List<Autor> autores = autorRepository.findAutoresVivosEnAnioConLibros(anio);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
            return;
        }

        for (Autor autor : autores) {
            System.out.println("\nAutor: " + autor.getNombre());
            System.out.println("Nacimiento: " + (autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "Fecha desconocida"));
            System.out.println("Fallecimiento: " + (autor.getFechaFallecimiento() != null ? autor.getFechaFallecimiento() : "—"));

            if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
                System.out.println("Libros:");
                autor.getLibros().forEach(libro ->
                        System.out.println("  - " + libro.getTitulo())
                );
            } else {
                System.out.println("Libros: (sin libros registrados)");
            }

            System.out.println("---------------------------");
        }
    }

    public void listarLibrosPorIdioma() {
        System.out.println("Seleccione idioma:");
        System.out.println("EN - Inglés");
        System.out.println("SP - Español");
        System.out.println("FR - Francés");
        System.out.println("PR - Portugués");
        String idioma = teclado.nextLine().toLowerCase();

        libroRepository.findByIdiomaIgnoreCase(idioma)
                .forEach(l -> System.out.println(l.getTitulo()));
    }

}
