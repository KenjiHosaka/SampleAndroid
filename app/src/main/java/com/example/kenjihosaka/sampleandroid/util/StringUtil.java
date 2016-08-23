package com.example.kenjihosaka.sampleandroid.util;

import java.util.regex.Pattern;

public class StringUtil {

    private static final String  WHITE_SPACES   = "[\\s　]*";
    private static final Pattern BLANK_STRING   = Pattern.compile("^"+ WHITE_SPACES + "$");

    public static boolean isBlank(String str) {
        return str == null || BLANK_STRING.matcher(str).find();
    }

}
