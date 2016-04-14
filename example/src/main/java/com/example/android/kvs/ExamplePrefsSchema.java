package com.example.android.kvs;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.Set;

@Table(name = "example", builder = ExamplePrefsBuilder.class)
public abstract class ExamplePrefsSchema {
    @Key("user_id")
    long userId;
    @Key("user_name")
    String userName;
    @Key("user_age")
    int userAge;
    @Key("guest_flag")
    boolean guestFlag;
    @Key("progress")
    float progress;
    @Key("search_history")
    Set<String> searchHistory;
}
