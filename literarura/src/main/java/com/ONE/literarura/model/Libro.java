package com.ONE.literarura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    // Solo guardamos el primer idioma (según consigna del challenge)
    private String idioma;

    private Integer numeroDeDescargas;

    // Solo guardamos el primer autor (según consigna del challenge)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
                fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    // Constructor vacío requerido por JPA
    public Libro() {}

    // Constructor desde DatosLibro (DTO de la API)
    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas().isEmpty() ? "Desconocido" : datosLibro.idiomas().get(0);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Integer getNumeroDeDescargas() { return numeroDeDescargas; }
    public void setNumeroDeDescargas(Integer numeroDeDescargas) { this.numeroDeDescargas = numeroDeDescargas; }
    public List<Autor> getAutores() { return autores; }
    public void setAutores(List<Autor> autores) { this.autores = autores; }

    @Override
    public String toString() {
        String autor = (autores != null && !autores.isEmpty())
                ? autores.get(0).getNombre()
                : "Desconocido";
        return """
                ╔══════════════════════════════════════╗
                   LIBRO
                   Título: %s
                   Autor: %s
                   Idioma: %s
                   Número de descargas: %,d
                ╚══════════════════════════════════════╝
                """.formatted(titulo, autor, idioma, numeroDeDescargas);
    }
}
