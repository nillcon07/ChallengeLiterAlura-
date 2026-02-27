# 📚 LiterAlura — Challenge Oracle Next Education

Aplicación de consola desarrollada en **Java + Spring Boot** que permite buscar libros a través de la API de [Gutendex](https://gutendex.com/) y guardarlos en una base de datos **PostgreSQL** para su posterior consulta.

---

## 🚀 Tecnologías utilizadas

| Tecnología | Versión |
|---|---|
| Java | 25 (compilado con target 21) |
| Spring Boot | 3.4.3 |
| Spring Data JPA | 3.4.3 |
| Hibernate | 6.6.8 |
| PostgreSQL | 18+ |
| Jackson | 2.18.2 |
| Maven | Wrapper incluido |

---

## 📋 Funcionalidades

| Opción | Descripción |
|---|---|
| 1 | Buscar libro por título en la API de Gutendex y guardar en BD |
| 2 | Listar todos los libros registrados en la base de datos |
| 3 | Listar todos los autores registrados en la base de datos |
| 4 | Listar autores vivos en un determinado año |
| 5 | Listar libros por idioma (es, en, fr, pt...) |
| 0 | Salir de la aplicación |

---

## 🗂️ Estructura del proyecto

```
src/main/java/com/ONE/literarura/
├── LiteraruraApplication.java      # Punto de entrada (CommandLineRunner)
├── model/
│   ├── Autor.java                  # Entidad JPA — tabla autores
│   ├── Libro.java                  # Entidad JPA — tabla libros
│   ├── DatosAutor.java             # Record DTO (respuesta JSON API)
│   ├── DatosLibro.java             # Record DTO (respuesta JSON API)
│   └── DatosRespuestaLibros.java   # Record DTO (respuesta paginada API)
├── principal/
│   └── Principal.java              # Menú interactivo con Scanner
├── repository/
│   ├── AutorRepository.java        # Spring Data JPA + derived queries
│   └── LibroRepository.java        # Spring Data JPA + derived queries
└── service/
    ├── ConsumoAPI.java             # HttpClient + HttpRequest + HttpResponse
    └── ConvierteDatos.java         # Jackson ObjectMapper
```

---

## ⚙️ Configuración y ejecución

### Prerrequisitos

- [JDK 21+](https://adoptium.net/) instalado
- [PostgreSQL](https://www.postgresql.org/download/) instalado y corriendo
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (recomendado) o Maven en el PATH

### 1. Clonar el repositorio

```bash
git clone https://github.com/nillcon07/ChallengeLiterAlura-.git
cd ChallengeLiterAlura-
```

### 2. Crear la base de datos en PostgreSQL

Ejecutá esto en **pgAdmin** o **psql**:

```sql
CREATE DATABASE literarura_db;
```

### 3. Configurar credenciales

Editá el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literarura_db
spring.datasource.username=postgres
spring.datasource.password=TU_PASSWORD_AQUI
```

### 4. Ejecutar la aplicación

**Desde IntelliJ:** Abrí el proyecto → ejecutá `LiteraruraApplication.java`

**Desde terminal:**
```bash
./mvnw spring-boot:run      # Linux/Mac
mvnw.cmd spring-boot:run    # Windows
```

> **Nota:** Las tablas se crean automáticamente al iniciar la app gracias a `spring.jpa.hibernate.ddl-auto=update`

---

## 🔌 API Utilizada

**Gutendex** — API REST de libros del Proyecto Gutenberg  
Base URL: `https://gutendex.com/books/`  
Documentación: [gutendex.com](https://gutendex.com/)

Ejemplo de búsqueda:
```
GET https://gutendex.com/books/?search=don+quijote
```

---

## 🗄️ Modelo de base de datos

```
autores                    libros
──────────────────         ──────────────────────
id (PK)                    id (PK)
nombre (UNIQUE)            titulo (UNIQUE)
ano_nacimiento             idioma
ano_fallecimiento          numero_de_descargas
                           
              libros_autores (tabla intermedia)
              ─────────────────────────────────
              libro_id (FK → libros)
              autor_id (FK → autores)
```

---

## 👤 Autor

**Valentín** — Oracle Next Education (ONE) G8  
Repositorio: [ChallengeLiterAlura](https://github.com/nillcon07/ChallengeLiterAlura-.git)
