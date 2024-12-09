package com.literalura.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.BookApiResponse;
import com.literalura.models.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final HttpClient client;
    private final List<Book> catalog;

    public CatalogService() {
        this.client = HttpClient.newHttpClient();
        this.catalog = new ArrayList<>();
    }

    public Book fetchBookByTitle(String title) {
        String url = "https://gutendex.com/books/?search=" + title.replace(" ", "+");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                BookApiResponse apiResponse = objectMapper.readValue(response.body(), BookApiResponse.class);

                if (!apiResponse.getResults().isEmpty()) {
                    Book firstBook = apiResponse.getResults().get(0);
                    catalog.add(firstBook);
                    return firstBook;
                }
            } else {
                throw new RuntimeException("Erro ao buscar livro: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro na requisição HTTP", e);
        }

        return null;
    }

    public List<Book> listAllBooks() {
        return new ArrayList<>(catalog);
    }

    public List<Book> listBooksByLanguage(String language) {
        return catalog.stream()
                .filter(book -> book.getLanguages() != null && book.getLanguages().length > 0 && book.getLanguages()[0].equalsIgnoreCase(language))
                .collect(Collectors.toList());
    }
}
