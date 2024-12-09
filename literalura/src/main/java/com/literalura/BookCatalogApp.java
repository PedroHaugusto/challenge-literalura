package com.literalura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class BookCatalogApp implements CommandLineRunner {

    private final BookService bookService;
    private final List<Book> bookCatalog;

    public BookCatalogApp(BookService bookService) {
        this.bookService = bookService;
        this.bookCatalog = new ArrayList<>();
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            displayMenu();
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    searchBookByTitle(scanner);
                    break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    listAllAuthors();
                    break;
                case 4:
                    listAuthorsAliveInYear(scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 0);
    }

    private void displayMenu() {
        System.out.println("Selecione uma opção:");
        System.out.println("1. Buscar livro por título");
        System.out.println("2. Listar todos os livros");
        System.out.println("3. Listar todos os autores");
        System.out.println("4. Listar autores vivos em determinado ano");
        System.out.println("0. Sair");
    }

    private void searchBookByTitle(Scanner scanner) {
        System.out.print("Digite o título do livro: ");
        String title = scanner.next();
        BookApiResponse apiResponse = bookService.fetchBooks(); // Implementar busca por título na API
        // Filtrar pelo título correto e adicionar ao catálogo
        apiResponse.getResults().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .ifPresent(bookCatalog::add);
        System.out.println("Livro adicionado ao catálogo.");
    }

    private void listAllBooks() {
        System.out.println("Catálogo de livros:");
        bookCatalog.forEach(System.out::println);
    }

    private void listAllAuthors() {
        System.out.println("Autores disponíveis:");
        bookCatalog.forEach(book -> {
            Author author = book.getAuthor();
            if (author != null) {
                System.out.println("- " + author.getName());
            }
        });
    }

    private void listAuthorsAliveInYear(Scanner scanner) {
        System.out.print("Digite o ano para verificar autores vivos: ");
        int year = scanner.nextInt();
        System.out.println("Autores vivos no ano " + year + ":");
        bookCatalog.stream()
                .map(Book::getAuthor)
                .filter(author -> author != null &&
                        author.getBirthYear() != null &&
                        author.getBirthYear() <= year &&
                        (author.getDeathYear() == null || author.getDeathYear() > year))
                .forEach(author -> System.out.println("- " + author.getName()));
    }
}