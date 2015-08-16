package com.example.android.kvs;

import android.content.Context;

import com.rejasupotaro.android.kvs.PrefSchema;
import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

@Table("example")
public abstract class ExamplePrefsSchema extends PrefSchema {
    public static ExamplePrefs prefs;

    @Key("int_value")
    protected int intValue;
    @Key("long_value")
    protected long longValue;
    @Key("float_value")
    protected float floatValue;
    @Key("boolean_value")
    protected boolean booleanValue;
    @Key("string_value")
    protected String stringValue = "guest";

    public static synchronized ExamplePrefs create(Context context) {
        if (prefs == null) {
            prefs = new ExamplePrefs(context);
        }
        return prefs;
    }
}
