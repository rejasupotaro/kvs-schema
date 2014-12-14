package com.example.android.kvs;

import android.content.Context;

import com.rejasupotaro.android.kvs.PrefSchema;
import com.rejaupotaro.android.kvs.annotations.Key;
import com.rejaupotaro.android.kvs.annotations.Kvs;

@Kvs(name = "sample")
public abstract class SampleSchema extends PrefSchema {
    @Key("user_id") int userId;
    @Key("user_name") String userName;

    public static Sample create(Context context) {
        return new Sample(context);
    }
}
