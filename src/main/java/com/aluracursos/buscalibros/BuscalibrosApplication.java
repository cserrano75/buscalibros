package com.aluracursos.buscalibros;

import com.aluracursos.buscalibros.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuscalibrosApplication implements CommandLineRunner {

	private final Principal principal;

	public BuscalibrosApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(BuscalibrosApplication.class, args);
	}

	@Override
	public void run(String... args) {
		principal.mostrarMenu();
	}

//		System.out.println("Ingreso...");
//		var consumoApi = new ConsumoAPI();
//		var json = consumoApi.obtenerDatos("https://gutendex.com/books/");
//		System.out.println(json);
//
//		ConvierteDatos conversor = new ConvierteDatos();
////		var datos = conversor.obtenerDatos(json, DatosLibro.class);
////		System.out.println(datos);
//
//		//Busco los datos de los 10 primeros libros
//		List<DatosLibro> libros = new ArrayList<>();
//		for (int i = 1; i < 11; i++) {
//			json = consumoApi.obtenerDatos("https://gutendex.com/books/"+i);
//			var datosLibros = conversor.obtenerDatos(json, DatosLibro.class);
//			libros.add(datosLibros);
//		}
//
//		libros.forEach(System.out::println);

}

