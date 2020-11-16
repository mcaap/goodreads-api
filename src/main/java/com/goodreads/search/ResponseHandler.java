package com.goodreads.search;

import com.goodreads.search.model.Book;
import com.goodreads.search.util.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ResponseHandler {

    private static final Logger logger = Logger.getLogger(ResponseHandler.class.getSimpleName());

    List<Book> responseXmlToObject(String responseXml) {
        List<Book> lstBooks = new ArrayList<>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(responseXml.getBytes());
        try {
            Document document = dbFactory.newDocumentBuilder().parse(inputStream);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("work");
            int length = nodeList.getLength();
            if (Util.IS_DEBUG) {
                logger.info("node list size: " + length);
            }
            for (int i = 0; i < length; i++) {
                Node item = nodeList.item(i);
                Book book = itemToBook(item);
                if (Util.IS_DEBUG) {
                    logger.info("book: " + book);
                }
                lstBooks.add(book);
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return lstBooks;
    }

    private Book itemToBook(Node node) {
        Book book = new Book();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element workElement = (Element) node;
            book.setRatingsCount(getIntegerValue(workElement, "ratings_count"));
            book.setPublicationYear(getIntegerValue(workElement, "original_publication_year"));
            book.setAverageRating(getFloatValue(workElement, "average_rating"));

            Element bookElement = (Element) workElement.getElementsByTagName("best_book").item(0);
            book.setTitle(getStringValue(bookElement, "title"));
            book.setImageUrl(getStringValue(bookElement, "image_url"));
            book.setSmallImageUrl(getStringValue(bookElement, "small_image_url"));

            Element authorElement = (Element) workElement.getElementsByTagName("author").item(0);
            book.setAuthor(getStringValue(authorElement, "name"));
        }
        return book;
    }

    String getStringValue(Element element, String tag) {
        String value = null;
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null) {
            value = nodeList.item(0).getTextContent();
        }
        return value;
    }

    Integer getIntegerValue(Element element, String tag) {
        Integer intValue = null;
        String stringValue = getStringValue(element, tag);
        if (!Util.isTrivial(stringValue)) {
            intValue = Integer.valueOf(stringValue);
        }
        return intValue;
    }

    Float getFloatValue(Element element, String tag) {
        Float floatValue = null;
        String stringValue = getStringValue(element, tag);
        if (!Util.isTrivial(stringValue)) {
            floatValue = Float.valueOf(stringValue);
        }
        return floatValue;
    }

}
