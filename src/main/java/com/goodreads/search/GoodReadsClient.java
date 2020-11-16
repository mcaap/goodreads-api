package com.goodreads.search;

import com.goodreads.search.http.HttpClient;
import com.goodreads.search.http.WebUtil;
import com.goodreads.search.model.Book;
import com.goodreads.search.util.GoodReadsConstants;
import com.goodreads.search.util.Util;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class GoodReadsClient implements GoodReadsClientInterface {

    private static final Logger logger = Logger.getLogger(GoodReadsClient.class.getSimpleName());

    private final String apiKey;
    private HttpClient httpClient;
    private ResponseHandler responseHandler = new ResponseHandler();

    public GoodReadsClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.getInstance();
    }

    @Override
    public List<Book> searchBooksByAuthorOrTitle(String term) {
        String qp = toQueryParams(term, null, null);
        return searchBooks(qp);
    }

    @Override
    public List<Book>  searchBooksByAuthor(String author) {
        String qp = toQueryParams(author, GoodReadsConstants.SEARCH_FIELDS.author.name(), null);
        return searchBooks(qp);
    }

    @Override
    public List<Book>  searchBooksByTitle(String title) {
        String qp = toQueryParams(title, GoodReadsConstants.SEARCH_FIELDS.title.name(), null);
        return searchBooks(qp);
    }

    @Nullable
    private List<Book> searchBooks(String qp) {
        List<Book> lstBooks = null;
        try {
            String url = getUrl(qp);
            if (Util.IS_DEBUG) {
                logger.info("url: " + url);
            }
            String response = httpClient.get(url);
            if (Util.IS_DEBUG) {
                logger.info("response: " + response);
            }
            if (!Util.isTrivial(response)) {
                lstBooks = responseHandler.responseXmlToObject(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lstBooks;
    }

    private String getUrl(String queryParams) {
        return GoodReadsConstants.APIS.SEARCH_BOOKS.getUrl() + "?" + queryParams;
    }

    @Nullable
    private String toQueryParams(String term, String field, String page) {
        String qp = null;
        HashMap<String, String> params = new HashMap<>();
        params.put(GoodReadsConstants.SEARCH_PARAMS.q.name(), term);
        params.put(GoodReadsConstants.SEARCH_PARAMS.key.name(), apiKey);
        params.put(GoodReadsConstants.SEARCH_PARAMS.page.name(), page);
        params.put(GoodReadsConstants.SEARCH_PARAMS.search.name(), field);
        try {
            qp = WebUtil.getParamsString(params);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return qp;
    }
}
