package com.literalura;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.print.Book;
import java.util.List;

public class BookApiResponse {
    private int count;
    private String next;
    private String previous;

    @JsonProperty("results")
    private List<Book> books;

    // Getters e Setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
