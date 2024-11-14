package com.aluracursos.desafio.GutendexBooksApp.main;

import com.aluracursos.desafio.GutendexBooksApp.model.*;
import com.aluracursos.desafio.GutendexBooksApp.service.ConsumeAPI;
import com.aluracursos.desafio.GutendexBooksApp.service.*;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final ConsumeAPI consumeAPI = new ConsumeAPI();
    private static final ConvertData converter = new ConvertData();
    private static final Scanner sc = new Scanner(System.in);
    private static final String URL_BASE = "http://gutendex.com/books/?";
    private static BookService bookService;
    private static AuthorService authorService;

    public Main(BookService bookService, AuthorService authorService) {
        Main.authorService = authorService;
        Main.bookService = bookService;
    }

    public void initApp() {


        int userOption;
        do {
            showMenu();
            System.out.print("\nIngresa la opción: ");
            userOption = sc.nextInt();
            sc.nextLine();
            switch (userOption) {
                case 1:
                    searchBookOnGutendexAPI();
                    break;
                case 2:
                    showAllRegisteredBooks();
                    break;
                case 3:
                    showAllRegisteredAuthors();
                    break;
                case 4:
                    showAllAuthorsByPeriodPastDate();
                    break;
                case 5:
                    filterBooksByLanguage();
                    break;
                case 6:
                    showStatsFromBooks();
                    break;
                case 7:
                    System.out.println("\nHa Salido de la Aplicación Gracias por usar nuestros servicios!");
                    break;
                default:
                    System.out.println("Opción Invalidad!!");
                    break;
            }
        } while (userOption != 7);
    }

    private static void showMenu() {

        System.out.println("\n1. Buscar Libro en la API de GutenDex por nombre");
        System.out.println("2. Mostrar todos los libros registrados");
        System.out.println("3. Mostrar todos los autores registrados");
        System.out.println("4. Mostrar todos los autores registrados en un periodo de tiempo");
        System.out.println("5. Mostrar todos los libros filtrados por idioma");
        System.out.println("6. Mostrar Estadisticas de los libros");
        System.out.println("7. Salir de la aplicación.");

    }

    private static void searchBookOnGutendexAPI() {

        System.out.println("Ingresa nombre del libro para buscar");
        var searchByUser = sc.nextLine();

        var json = consumeAPI.getData(URL_BASE + "search=" + searchByUser.replaceAll(" ", "%20"));
        // verify if we have some search Data
        SearchData searching = null;
        try {
            searching = converter.getData(json, SearchData.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Optional<BookData> bookSearched = searching.bookResults().stream()
                .filter(b -> b.title().toUpperCase().contains(searchByUser.toUpperCase()))
                .findFirst();

        if (bookSearched.isPresent()) {
            BookData bookDTO = bookSearched.get();
            System.out.println("El libro fue encontrado con exito!");
            Book book = new Book(bookDTO);

            Optional<Book> existingBook = Optional.ofNullable(bookService.getBookByTitle(bookDTO.title()));
            if (existingBook.isPresent()) {
                System.out.println("El libro ya existe en la base de datos");
                book = existingBook.get();
                System.out.println(book);
            } else {
                for (AuthorData authorData : bookDTO.authors()) {
                    Author author;
                    Optional<Author> existingAuthor = Optional.ofNullable(authorService.getAuthorByName(authorData.name()));
                    if (existingAuthor.isPresent()) {
                        author = existingAuthor.get();
                    } else {
                        author = new Author(authorData);
                        authorService.saveAuthor(author);
                    }
                    book.addAuthor(author);
                }
                bookService.saveBook(book);
                System.out.println("Libro guardado exitosamente");
                System.out.println(book);
            }
        } else {
            System.out.println("No se encontro ningun libro con ese nombre");
        }


    }

    private static void showAllRegisteredBooks() {
        List<Book> books = bookService.getAllBooks();
        books.forEach(System.out::println);
    }

    private static void showAllRegisteredAuthors() {
        List<Author> authors = authorService.getAllAuthors();


        authors.forEach(a -> System.out.printf(
                """
                        ------------AUTOR--------------
                        Id: %s
                        Nombre: %s
                        Año de Nacimiento: %s
                        Año de Fallecimiento: %s
                        --------------------------------
                        """.formatted(a.getId(), a.getName(), a.getBirthYear(), a.getDeathYear())
        ));


    }

    private static void showAllAuthorsByPeriodPastDate() {
        System.out.println("\nIngresa el año para filtrar autores");
        var year = sc.nextInt();

        List<Author> authorInPeriod = authorService.getAuthorsByPeriodPastDate(year);
        if (!authorInPeriod.isEmpty()) {
            authorInPeriod.forEach(a -> System.out.printf(
                    """
                            ------------AUTOR--------------
                            Id: %s
                            Nombre: %s
                            Año de Nacimiento: %s
                            Año de Fallecimiento: %s
                            --------------------------------
                            """.formatted(a.getId(), a.getName(), a.getBirthYear(), a.getDeathYear())
            ));
        } else {
            System.out.println("No hay autores con ese periodo de tiempo");
        }
    }

    public static void filterBooksByLanguage() {

        System.out.println();
        System.out.println("""
                Ingresa el idioma para buscar los libros
                1. Español - es
                2. Ingles - en 
                3. Italiano - it
                4. Portugues - pt
                5. Ruso - ru
                """);
        var language = sc.nextLine();

        Language languageEnum;
        try {
            languageEnum = Language.fromString(language.toLowerCase());

            if (languageEnum == null) {
                System.out.println("El idioma ingresado no es válido. Por favor, intenta de nuevo.");
                return;
            }

            List<Book> booksFiltered = bookService.getBooksFilteredByLanguage(languageEnum);
            if (!booksFiltered.isEmpty()) {

                System.out.println("La cantidad de libros filtrados son: " + booksFiltered.size());
                System.out.println("\nLos libro filtrados por el idioma " + languageEnum + " [" + languageEnum.getShortWord() + "]");
                booksFiltered.forEach(b -> System.out.printf(
                        """
                                ------------LIBRO--------------
                                Titulo: %s
                                Id: %s
                                Autores: %s
                                Descargas: %s
                                Lenguajes: %s
                                --------------------------------
                                """.formatted(b.getTitle(), b.getId(), b.getAuthors(), b.getLanguage(), b.getDownloadCounts())
                ));
            } else {
                System.out.println("No hay libros con ese lenguaje");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }


    private static void showStatsFromBooks() {
        List<Book> books = bookService.getAllBooks();
        System.out.println("\nTop 10 libros más descargados");
        books.stream()
                .sorted(Comparator.comparing(Book::getDownloadCounts).reversed())
                .limit(10)
                .forEach(b -> System.out.println(
                        "Cantidad descargas: " + b.getDownloadCounts() +
                                ", Libro Id: " + b.getId() +
                                ", Nombre Libro: " + b.getTitle().toUpperCase() +
                                ", Autores: " + b.getAuthors()
                ));

        DoubleSummaryStatistics stats = books.stream()
                .filter(e -> e.getDownloadCounts() > 0)
                .collect(Collectors.summarizingDouble(Book::getDownloadCounts));

        System.out.println("\nEstadisticas de libros registrados \n");
        System.out.println("Media de Descargas: " + stats.getAverage());
        System.out.println("Libro más descargado: " + stats.getMax());
        System.out.println("Libro menos descargado: " + stats.getMin());
        System.out.println("Cantidad libros procesados: " + stats.getCount());
    }
}