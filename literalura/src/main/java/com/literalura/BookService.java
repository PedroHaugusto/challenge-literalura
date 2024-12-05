package com.literalura;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BookService {

    private final HttpClient client;

    public BookService() {
        this.client = HttpClient.newHttpClient();
    }

    public BookApiResponse buscarLivros() {
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
}
