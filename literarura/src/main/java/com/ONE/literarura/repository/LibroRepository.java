package com.ONE.literarura.repository;

import com.ONE.literarura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainingIgnoreCase(String titulo);

    // Listar libros filtrados por idioma (derived query)
    List<Libro> findByIdioma(String idioma);
}
