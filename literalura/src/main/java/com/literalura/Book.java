package com.literalura;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private int id;

    @JsonAlias({"title"})
    private String bookTitle;

    private List<Author> authors;

    @JsonProperty("download_count")
    private int downloadCount;

    // Getters, Setters e toString
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        StringBuilder authorNames = new StringBuilder();
        if (authors != null) {
            authors.forEach(author -> authorNames.append(author.getName()).append(", "));
        }
        return "Book{" +
                "id=" + id +
                ", title='" + bookTitle + '\'' +
                ", authors=" + (!authorNames.isEmpty() ? authorNames.substring(0, authorNames.length() - 2) : "N/A") +
                ", downloads=" + downloadCount +
                '}';
    }
}
