package com.example.android.kvs.prefs.schemas;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.Set;

@Table(name = "test_with_default")
public abstract class TestWithDefaultPrefsSchema {
    @Key("long_value")
    final long longValue = 99999999999L;
    @Key("string_value")
    final String stringValue = "abc";
    @Key("int_value")
    final int intValue = -1;
    @Key("boolean_value")
    final boolean booleanValue = true;
    @Key("float_value")
    final float floatValue = -1.0f;
    @Key("string_set_value")
    Set<String> stringSetValue;
}

