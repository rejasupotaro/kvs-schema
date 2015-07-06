package com.example.android.kvs;

import android.content.Context;

import com.rejasupotaro.android.kvs.PrefSchema;
import com.rejasupotaro.android.kvs.Key;
import com.rejasupotaro.android.kvs.Table;

@Table("example")
public abstract class ExamplePrefsSchema extends PrefSchema {
    public static ExamplePrefs prefs;

    @Key("user_id")
    protected long userId = -1;
    @Key("user_name")
    protected String userName = "guest";

    public static synchronized ExamplePrefs get(Context context) {
        if (prefs == null) {
            prefs = new ExamplePrefs(context);
        }
        return prefs;
    }
}
