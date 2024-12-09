package com.literalura.services;

import com.literalura.HttpClientProvider;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LivroService {

    private final HttpClientProvider httpClientProvider;

    public LivroService() {
        this.httpClientProvider = new HttpClientProvider();
    }

    public String buscarLivros() {
        HttpClient client = httpClientProvider.createClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books"))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RuntimeException("Erro ao buscar livros: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao realizar a requisição HTTP", e);
        }
    }
}
