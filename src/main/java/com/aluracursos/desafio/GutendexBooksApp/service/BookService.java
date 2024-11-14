package com.aluracursos.desafio.GutendexBooksApp.service;

import com.aluracursos.desafio.GutendexBooksApp.model.Book;
import com.aluracursos.desafio.GutendexBooksApp.model.Language;
import com.aluracursos.desafio.GutendexBooksApp.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public void saveBook(Book book){
        repository.save(book);
    }

    public void deleteBook(Book book){
        repository.delete(book);
    }

    public void updateBook(Book book){
        repository.save(book);
    }

    @Transactional
    public Book getBookByTitle(String title){
        return repository.findByTitle(title);
    }

    public List<Book> getAllBooks(){
        return repository.findAll();
    }

    public List<Book> getBooksFilteredByLanguage(Language language){
        return repository.findByLanguage(language);
    }
}
