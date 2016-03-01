package com.example.android.kvs;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

import java.util.Set;

@Table("example")
public abstract class ExamplePrefsSchema {
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
}
