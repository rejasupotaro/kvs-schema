package com.example.android.kvs.prefs.schemas;

import com.example.android.kvs.prefs.serializer.UserIntSerializer;
import com.example.android.kvs.prefs.serializer.UserStringSerializer;
import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

@Table(name = "test_with_serializer")
public class TestWithSerializerPrefsSchema {
    @Key(name = "user_string", serializer = UserStringSerializer.class)
    String userString;
    @Key(name = "user_int", serializer = UserIntSerializer.class)
    int userInt;
}
