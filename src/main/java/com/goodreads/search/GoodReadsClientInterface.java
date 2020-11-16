package com.goodreads.search;

import com.goodreads.search.model.Book;

import java.util.List;

public interface GoodReadsClientInterface {

    /**
     * Returns the first 20 results where author or title matches the search term
     * @param term
     * @return
     */
    List<Book> searchBooksByAuthorOrTitle(String term);

    List<Book> searchBooksByAuthor(String author);

    List<Book> searchBooksByTitle(String title);
}
