package com.goodreads.search.util;

public class GoodReadsConstants {

    public static final String BASE_URL = "https://www.goodreads.com";

    public enum APIS {
        SEARCH_BOOKS("/search/index.xml");
        private final String url;

        APIS(String url) {
            this.url = BASE_URL + url;
        }

        public String getUrl() {
            return url;
        }
    }

    public enum SEARCH_PARAMS {
        q, page, key, search
    }

    public enum SEARCH_FIELDS {
        all, title, author
    }
}
