package com.ONE.literarura.principal;

import com.ONE.literarura.model.Autor;
import com.ONE.literarura.model.DatosAutor;
import com.ONE.literarura.model.DatosLibro;
import com.ONE.literarura.model.DatosRespuestaLibros;
import com.ONE.literarura.model.Libro;
import com.ONE.literarura.repository.AutorRepository;
import com.ONE.literarura.repository.LibroRepository;
import com.ONE.literarura.service.ConsumoAPI;
import com.ONE.literarura.service.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    
                    ╔══════════════════════════════════════════╗
                    ║          LiterAlura — Menú Principal     ║
                    ╠══════════════════════════════════════════╣
                    ║  1 - Buscar libro por título             ║
                    ║  2 - Listar libros registrados           ║
                    ║  3 - Listar autores registrados          ║
                    ║  4 - Listar autores vivos en un año      ║
                    ║  5 - Listar libros por idioma            ║
                    ║  0 - Salir                               ║
                    ╚══════════════════════════════════════════╝
                    Elige una opción:\s""";
            System.out.print(menu);

            try {
                opcion = Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Opción inválida. Ingresa un número.");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosEnAnio();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("\n👋 ¡Hasta luego! Cerrando LiterAlura...\n");
                default -> System.out.println("⚠️  Opción inválida. Elige entre 0 y 5.");
            }
        }
        teclado.close();
    }

    // ──────────────────────────────────────────────────
    // 1. Buscar libro por título (en API y guardar en BD)
    // ──────────────────────────────────────────────────
    private void buscarLibroPorTitulo() {
        System.out.print("\n🔍 Ingresa el título del libro a buscar: ");
        String titulo = teclado.nextLine().trim();

        if (titulo.isBlank()) {
            System.out.println("⚠️  El título no puede estar vacío.");
            return;
        }

        // Verificar si ya existe en la BD
        Optional<Libro> libroExistente = libroRepository.findByTituloContainingIgnoreCase(titulo);
        if (libroExistente.isPresent()) {
            System.out.println("\n📌 Este libro ya está registrado en la base de datos:");
            System.out.println(libroExistente.get());
            return;
        }

        // Consultar la API
        String url = URL_BASE + "?search=" + titulo.replace(" ", "%20");
        String json = consumoAPI.obtenerDatos(url);
        DatosRespuestaLibros respuesta = convierteDatos.obtenerDatos(json, DatosRespuestaLibros.class);

        if (respuesta.resultados().isEmpty()) {
            System.out.println("❌ No se encontró ningún libro con ese título en la API.");
            return;
        }

        // Tomar el primer resultado
        DatosLibro datosLibro = respuesta.resultados().get(0);
        Libro libro = new Libro(datosLibro);

        // Procesar el autor (solo el primero, según consigna)
        if (!datosLibro.autores().isEmpty()) {
            DatosAutor datosAutor = datosLibro.autores().get(0);
            Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                    .orElseGet(() -> new Autor(datosAutor));
            libro.setAutores(List.of(autor));
        }

        libroRepository.save(libro);
        System.out.println("\n✅ Libro guardado exitosamente:");
        System.out.println(libro);
    }

    // ──────────────────────────────────────────────────
    // 2. Listar todos los libros registrados en la BD
    // ──────────────────────────────────────────────────
    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("\n📭 No hay libros registrados aún. ¡Busca alguno primero!");
            return;
        }
        System.out.println("\n📚 Libros registrados (" + libros.size() + " en total):");
        libros.forEach(System.out::println);
    }

    // ──────────────────────────────────────────────────
    // 3. Listar todos los autores registrados en la BD
    // ──────────────────────────────────────────────────
    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("\n📭 No hay autores registrados aún.");
            return;
        }
        System.out.println("\n🖊️  Autores registrados (" + autores.size() + " en total):");
        autores.forEach(System.out::println);
    }

    // ──────────────────────────────────────────────────
    // 4. Listar autores vivos en un determinado año
    // ──────────────────────────────────────────────────
    private void listarAutoresVivosEnAnio() {
        System.out.print("\n📅 Ingresa el año para buscar autores vivos: ");
        try {
            int anio = Integer.parseInt(teclado.nextLine().trim());
            List<Autor> autoresVivos = autorRepository.findAutoresVivosEnAnio(anio);
            if (autoresVivos.isEmpty()) {
                System.out.println("❌ No se encontraron autores vivos en el año " + anio + ".");
            } else {
                System.out.println("\n🧑‍💼 Autores vivos en " + anio + " (" + autoresVivos.size() + " encontrados):");
                autoresVivos.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️  El año debe ser un número entero válido.");
        }
    }

    // ──────────────────────────────────────────────────
    // 5. Listar libros por idioma
    // ──────────────────────────────────────────────────
    private void listarLibrosPorIdioma() {
        String menuIdiomas = """
                
                🌐 Selecciona un idioma:
                   es - Español
                   en - Inglés
                   fr - Francés
                   pt - Portugués
                Ingresa el código de idioma:\s""";
        System.out.print(menuIdiomas);
        String idioma = teclado.nextLine().trim().toLowerCase();

        if (idioma.isBlank()) {
            System.out.println("⚠️  Debes ingresar un código de idioma.");
            return;
        }

        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("❌ No se encontraron libros en el idioma: " + idioma);
        } else {
            System.out.println("\n📖 Libros en idioma '" + idioma + "' (" + libros.size() + " encontrados):");
            libros.forEach(System.out::println);
        }
    }
}
