package com.aluracursos.desafio.GutendexBooksApp.main;

import com.aluracursos.desafio.GutendexBooksApp.model.Author;
import com.aluracursos.desafio.GutendexBooksApp.model.BookData;
import com.aluracursos.desafio.GutendexBooksApp.model.SearchData;
import com.aluracursos.desafio.GutendexBooksApp.service.ConsumeAPI;
import com.aluracursos.desafio.GutendexBooksApp.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final ConsumeAPI consumeAPI = new ConsumeAPI();
    private static final ConvertData converter = new ConvertData();
    private static final Scanner sc = new Scanner(System.in);
    private static final String URL_BASE = "http://gutendex.com/books/?";
    private static int userOption;

    public void initApp(){

        System.out.println("$$$$$$$$$$$-- WELCOME TO OUR GUTENDEXBOOKS APP% -- $$$$$$$$$$");
        System.out.println("Ingresa nombre de libro o autores para buscar");
        var searchByUser = sc.nextLine();

        var json = consumeAPI.getData(URL_BASE+"search="+searchByUser.replaceAll(" ", "%20"));
        // verify if we have some search Data
        SearchData searching = null;
        try {
            searching = converter.getData(json, SearchData.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // if our search had success
        List<BookData> bookList = new ArrayList<>();
        if (searching.numberResults()!=0){
            System.out.println("Los Datos de la busquedad son los siguients");
            System.out.println("Resultados : "+ searching.numberResults());
            bookList = searching.bookResults();

            bookList.forEach(b -> System.out.println(
                                    "Libro Id: "+ b.bookId()+
                                            ", Nombre Libro: "+ b.name()+
                                            ", Autores: "+ b.authors().stream().map(Author::new)
                                            .toList()
                            ));

            do {
                showMenu();
                System.out.print("\nIngresa la opción: ");
                userOption = sc.nextInt();

                switch (userOption) {
                    case 1:
                        filterByDownlaodCounts(bookList);
                        break;
                    case 2:
                        filterByBookId(bookList);
                        break;
                    case 3:
                        filterByName(bookList);
                        break;
                    case 4:
                        getStatsFromBooks(bookList);
                        break;
                    case 5:
                        filterByAuthorAverageAge(bookList);
                        break;
                    case 6:
                        System.out.println("\nHa Salido de la Aplicación Gracias por usar nuestros servicios!");
                        break;
                    default:
                        System.out.println("Opción Invalidad!!");
                        break;
                }
            } while (userOption!= 6);


            System.out.println("\nLibros disponibles: "+bookList.size());
        } else {
            System.out.println("No se encontrarón resultados para esta busquedad");
        }

    }

    public static void showMenu() {

        System.out.println("\n1. Filtrar top 10 libros más descargados");
        System.out.println("2. Filtrar Libros por Id");
        System.out.println("3. Filtrar Libros por Nommbre");
        System.out.println("4. Mostrar Estadisticas de los libros");
        System.out.println("5. Filtrar por la edad de los autores (media si son mas de uno)");
        System.out.println("6. Salir de la aplicación.");

    }

    public static void filterByBookId(List<BookData> books){
        System.out.println("\nLibros Ordenados por Id\n");
        books.stream()
                .sorted(Comparator.comparing(BookData::bookId))
                .forEach(b -> System.out.println(
                        "Libro Id: "+ b.bookId()+
                        ", Nombre Libro: "+ b.name()+
                        ", Autores: "+ b.authors().stream().map(Author::new)
                                .toList()
                ));


    }

    public static void filterByDownlaodCounts(List<BookData> books){
        System.out.println("\nTop 10 libros más descargados");
        books.stream()
                .sorted(Comparator.comparing(BookData::downloadCounts).reversed())
                .limit(10)
                .forEach(b -> System.out.println(
                        "Cantidad descargas: "+b.downloadCounts()+
                        ", Libro Id: "+ b.bookId()+
                        ", Nombre Libro: "+ b.name().toUpperCase()+
                        ", Autores: "+ b.authors().stream().map(Author::new)
                                .toList()
                ));
    }

    public static void filterByName(List<BookData> books){
        System.out.println("\nLibros Filtrados por nombre");
        books.stream()
                .sorted(Comparator.comparing(BookData::name))
                .forEach(b -> System.out.println(
                        "Nombre Libro: "+ b.name()+
                        ", Cantidad descargas: "+b.downloadCounts()+
                                ", Libro Id: "+ b.bookId()+
                                ", Autores: "+ b.authors().stream().map(Author::new)
                                .toList()
                ));
    }

    public static void  getStatsFromBooks(List<BookData> books){
        DoubleSummaryStatistics stats = books.stream()
                .filter(e -> e.downloadCounts() >0)
                .collect(Collectors.summarizingDouble(BookData::downloadCounts));

        System.out.println("\nEstadisticas de busquedad \n");
        System.out.println("Media de Descargas: " + stats.getAverage());
        System.out.println("Libro más descargado: "+ stats.getMax());
        System.out.println("Libro menos descargado: "+ stats.getMin());
        System.out.println("Cantidad libros procesados: " + stats.getCount());
    }

    public static void filterByAuthorAverageAge(List<BookData> books){
        System.out.println("\nListado filtrado por la edad de los autores\n");
        books.stream()
                .flatMap(b -> b.authors().stream()
                        .map(Author::new))
                .toList().stream().sorted(Comparator.comparing(Author::getAgeOfDeath))
                .forEach(System.out::println);
    }
}
