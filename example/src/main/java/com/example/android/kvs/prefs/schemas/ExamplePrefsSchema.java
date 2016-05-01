package com.example.android.kvs.prefs.schemas;

import com.example.android.kvs.prefs.ExamplePrefsBuilder;
import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.Set;

@Table(name = "example", builder = ExamplePrefsBuilder.class)
public abstract class ExamplePrefsSchema {
    @Key(name = "user_id")
    long userId;
    @Key(name = "user_name")
    String userName;
    @Key(name = "user_age")
    int userAge;
    @Key(name = "guest_flag")
    boolean guestFlag;
    @Key(name = "progress")
    float progress;
    @Key(name = "search_history")
    Set<String> searchHistory;
}
