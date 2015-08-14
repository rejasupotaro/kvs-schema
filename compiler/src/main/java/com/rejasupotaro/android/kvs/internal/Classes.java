package com.rejasupotaro.android.kvs.internal;

import com.squareup.javapoet.ClassName;

public class Classes {
    public static final String STRING = "java.lang.String";
    public static final ClassName CONTEXT = ClassName.get("android.content", "Context");
    public static final ClassName PREFS = ClassName.get("android.content", "SharedPreferences");
}
