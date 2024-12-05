package com.literalura;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class BookService {

    private final HttpClient client;

    public BookService() {
        this.client = HttpClient.newHttpClient();
    }

    public BookApiResponse fetchBooks() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books/"))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.body(), BookApiResponse.class);
            } else {
                throw new RuntimeException("Erro ao buscar livros: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro na requisição HTTP", e);
        }
    }

    public Book fetchBookById(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books/" + id + "/"))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.body(), Book.class);
            } else if (response.statusCode() == 404) {
                return null;
            } else {
                throw new RuntimeException("Erro ao buscar livro: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro na requisição HTTP", e);
        }
    }

    public void displayBooks(BookApiResponse apiResponse) {
        if (apiResponse != null && apiResponse.getResults() != null) {
            apiResponse.getResults().forEach(book -> {
                System.out.println("ID: " + book.getId());
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autores:");
                book.getAuthors().forEach(author -> {
                    System.out.println("  Nome: " + author.getName());
                    System.out.println("  Ano de nascimento: " + author.getBirthYear());
                    System.out.println("  Ano de falecimento: " + author.getDeathYear());
                });
                System.out.println("-----");
            });
        } else {
            System.out.println("Nenhum livro encontrado.");
        }
    }
}
