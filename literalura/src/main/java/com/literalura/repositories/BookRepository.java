package com.literalura.repositories;

import com.literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    long countByLanguage(String language);
}
