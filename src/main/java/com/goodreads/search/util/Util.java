package com.goodreads.search.util;

public class Util {

    public static boolean isTrivial(String ratingsCount) {
        return ratingsCount == null || ratingsCount.length() == 0;
    }
    public static boolean IS_DEBUG = true;
}
