package com.literalura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class BookCatalogApp implements CommandLineRunner {

    private final CatalogService catalogService;

    public BookCatalogApp(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Catálogo de Livros ===");
            System.out.println("1. Buscar livro por título");
            System.out.println("2. Listar todos os livros");
            System.out.println("3. Listar livros por idioma");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Digite o título do livro: ");
                    String title = scanner.nextLine();
                    Book book = catalogService.fetchBookByTitle(title);

                    if (book != null) {
                        System.out.println("\nLivro encontrado:");
                        System.out.println("Título: " + book.getTitle());
                        System.out.println("Autor: " + (book.getAuthors().length > 0 ? book.getAuthors()[0].getName() : "N/A"));
                        System.out.println("Idioma: " + (book.getLanguages().length > 0 ? book.getLanguages()[0] : "N/A"));
                        System.out.println("Downloads: " + book.getDownloadCount());
                    } else {
                        System.out.println("Nenhum livro encontrado com esse título.");
                    }
                }
                case 2 -> {
                    List<Book> books = catalogService.listAllBooks();
                    if (books.isEmpty()) {
                        System.out.println("Nenhum livro no catálogo.");
                    } else {
                        System.out.println("\nLivros no catálogo:");
                        books.forEach(book -> {
                            System.out.println("Título: " + book.getTitle());
                            System.out.println("Idioma: " + (book.getLanguages().length > 0 ? book.getLanguages()[0] : "N/A"));
                            System.out.println("Downloads: " + book.getDownloadCount());
                            System.out.println("-----");
                        });
                    }
                }
                case 3 -> {
                    System.out.print("Digite o idioma (ex: en): ");
                    String language = scanner.nextLine();
                    List<Book> booksByLanguage = catalogService.listBooksByLanguage(language);

                    if (booksByLanguage.isEmpty()) {
                        System.out.println("Nenhum livro encontrado para o idioma informado.");
                    } else {
                        System.out.println("\nLivros no idioma '" + language + "':");
                        booksByLanguage.forEach(book -> {
                            System.out.println("Título: " + book.getTitle());
                            System.out.println("Downloads: " + book.getDownloadCount());
                            System.out.println("-----");
                        });
                    }
                }
                case 4 -> {
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
