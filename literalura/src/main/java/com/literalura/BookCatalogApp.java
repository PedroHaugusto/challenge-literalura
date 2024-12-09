package com.literalura;

import com.literalura.models.Author;
import com.literalura.models.Book;
import com.literalura.services.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class BookCatalogApp implements CommandLineRunner {

    private final BookService bookService;

    public BookCatalogApp(BookService bookService) {
        this.bookService = bookService;
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
        System.out.println("1. Buscar livro por título e salvar");
        System.out.println("2. Listar todos os livros");
        System.out.println("3. Listar todos os autores");
        System.out.println("4. Listar autores vivos em determinado ano");
        System.out.println("0. Sair");
    }

    private void searchBookByTitle(Scanner scanner) {
        System.out.print("Digite o título do livro: ");
        String title = scanner.next();
        Author author = new Author();
        author.setName("Autor Exemplo");
        author.setBirthYear(1900);
        author.setDeathYear(1980);
        Book book = new Book();
        book.setTitle(title);
        book.setLanguage("EN");
        book.setDownloadCount(1000);
        book.setAuthor(author);

        bookService.saveBook(book);
        System.out.println("Livro salvo no banco.");
    }

    private void listAllBooks() {
        List<Book> books = bookService.getAllBooks();
        books.forEach(System.out::println);
    }

    private void listAllAuthors() {
        List<Author> authors = bookService.getAllAuthors();
        authors.forEach(System.out::println);
    }

    private void listAuthorsAliveInYear(Scanner scanner) {
        System.out.print("Digite o ano para verificar autores vivos: ");
        int year = scanner.nextInt();

        List<Author> authors = bookService.getAllAuthors();
        authors.stream()
                .filter(author -> author.getBirthYear() != null &&
                        author.getBirthYear() <= year &&
                        (author.getDeathYear() == null || author.getDeathYear() > year))
                .forEach(System.out::println);
    }
}