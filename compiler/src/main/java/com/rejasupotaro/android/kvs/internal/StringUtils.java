package com.rejasupotaro.android.kvs.internal;

public final class StringUtils {

    private StringUtils() {
    }

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }
}
