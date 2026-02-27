package com.ONE.literarura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private Integer anoNacimiento;
    private Integer anoFallecimiento;

    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros;

    // Constructor vacío requerido por JPA
    public Autor() {}

    // Constructor desde DatosAutor (DTO de la API)
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anoNacimiento = datosAutor.anoNacimiento();
        this.anoFallecimiento = datosAutor.anoFallecimiento();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getAnoNacimiento() { return anoNacimiento; }
    public void setAnoNacimiento(Integer anoNacimiento) { this.anoNacimiento = anoNacimiento; }
    public Integer getAnoFallecimiento() { return anoFallecimiento; }
    public void setAnoFallecimiento(Integer anoFallecimiento) { this.anoFallecimiento = anoFallecimiento; }
    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }

    @Override
    public String toString() {
        String fallecimiento = anoFallecimiento != null ? String.valueOf(anoFallecimiento) : "Presente";
        return """
                ╔══════════════════════════════════════╗
                   AUTOR
                   Nombre: %s
                   Año de nacimiento: %s
                   Año de fallecimiento: %s
                ╚══════════════════════════════════════╝
                """.formatted(
                nombre,
                anoNacimiento != null ? anoNacimiento : "Desconocido",
                fallecimiento
        );
    }
}
