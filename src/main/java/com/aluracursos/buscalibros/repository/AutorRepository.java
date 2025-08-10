package com.aluracursos.buscalibros.repository;

import com.aluracursos.buscalibros.model.Autor;
import com.aluracursos.buscalibros.model.Libro;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
//    @Query("SELECT a FROM Autor a WHERE (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento > :anio) AND a.fechaNacimiento <= :anio")
//    List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);

    @Query("""
        SELECT DISTINCT a
        FROM Autor a
        LEFT JOIN FETCH a.libros l
        WHERE (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento > :anio)
          AND a.fechaNacimiento <= :anio
        """)
    List<Autor> findAutoresVivosEnAnioConLibros(@Param("anio") Integer anio);


    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllAutoresConLibros();
}
