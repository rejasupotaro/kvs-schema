package com.example.android.kvs;

import android.content.Context;

import com.rejasupotaro.android.kvs.PrefSchema;
import com.rejaupotaro.android.kvs.annotations.Key;
import com.rejaupotaro.android.kvs.annotations.Kvs;

@Kvs(name = "example")
public abstract class ExampleSchema extends PrefSchema {
    @Key("user_id") int userId;
    @Key("user_name") String userName;

    public static Example create(Context context) {
        return new Example(context);
    }
}
