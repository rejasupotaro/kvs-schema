package com.example.android.kvs;

import android.content.Context;

import com.rejasupotaro.android.kvs.PrefSchema;
import com.rejaupotaro.android.kvs.annotations.Key;
import com.rejaupotaro.android.kvs.annotations.Table;

@Table("example")
public abstract class ExampleSchema extends PrefSchema {
    @Key("user_id") protected int userId = -1;
    @Key("user_name") protected String userName = "guest";

    public static Example create(Context context) {
        return new Example(context);
    }
}
