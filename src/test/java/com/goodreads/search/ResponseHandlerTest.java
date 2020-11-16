package com.goodreads.search;

import com.goodreads.search.model.Book;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ResponseHandlerTest {

    @Test
    public void responseXmlToObject() throws IOException {
        ResponseHandler handler = new ResponseHandler();
        String response = readTestFile("src/main/resources/sample_response.xml");
        List<Book> searchResponse = handler.responseXmlToObject(response);
    }

    String readTestFile(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return content;
    }

}