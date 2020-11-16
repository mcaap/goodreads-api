package com.goodreads.search;

import com.goodreads.search.model.Book;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GoodReadsClientTest {

    private static final String API_KEY = "YOUR_GOODREADS_API_KEY";
    GoodReadsClientInterface client = new GoodReadsClient(API_KEY);

    @Test
    public void searchBooksByAuthorOrTitle() {
        List<Book> bookList = client.searchBooksByAuthorOrTitle("Pereira");
        assertEquals(20, bookList.size());
        Book book = bookList.get(0);
        assertNotNull(book.getTitle());
        assertNotNull(book.getAuthor());
        assertNotNull(book.getImageUrl());
        assertNotNull(book.getAverageRating());
        assertNotNull(book.getRatingsCount());
    }
}