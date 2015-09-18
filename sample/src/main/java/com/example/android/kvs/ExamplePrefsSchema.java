package com.example.android.kvs;

import android.content.Context;

import com.rejasupotaro.android.kvs.PrefSchema;
import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.Set;

@Table("example")
public abstract class ExamplePrefsSchema extends PrefSchema {
    public static ExamplePrefs prefs;

    @Key("user_id")
    long userId;
    @Key("user_name")
    String userName = "guest";
    @Key("user_age")
    int userAge;
    @Key("guest_flag")
    boolean guestFlag;
    @Key("search_history")
    Set<String> searchHistory;

    public static synchronized ExamplePrefs get(Context context) {
        if (prefs == null) {
            prefs = new ExamplePrefs(context);
        }
        return prefs;
    }
}
