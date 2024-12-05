package com.literalura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final BookService bookService;

    public Main(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            exibirMenu();

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1 -> listarLivros();
                    case 2 -> buscarLivroPorId(scanner);
                    case 0 -> {
                        System.out.println("Saindo do programa. Até logo!");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Opção inválida. Por favor, tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Listar livros");
        System.out.println("2. Buscar livro por ID");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void listarLivros() {
        try {
            BookApiResponse response = bookService.fetchBooks();
            bookService.displayBooks(response);
        } catch (Exception e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
    }

    private void buscarLivroPorId(Scanner scanner) {
        System.out.print("Digite o ID do livro que deseja buscar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Book book = bookService.fetchBookById(id);
            if (book != null) {
                System.out.println(book);
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, insira um número inteiro.");
        } catch (Exception e) {
            System.out.println("Erro ao buscar o livro: " + e.getMessage());
        }
    }
}
