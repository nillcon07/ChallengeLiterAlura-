package com.ONE.literarura.repository;

import com.ONE.literarura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    // Autores vivos en un determinado año:
    // nacieron ANTES o EN ese año Y (no han fallecido AÚN o fallecieron DESPUÉS de ese año)
    @Query("SELECT a FROM Autor a WHERE a.anoNacimiento <= :anio AND (a.anoFallecimiento IS NULL OR a.anoFallecimiento >= :anio)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);
}
