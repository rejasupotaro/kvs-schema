package com.example.android.kvs.prefs.serializer;

import com.example.android.kvs.models.User;
import com.google.gson.Gson;
import com.rejasupotaro.android.kvs.serializers.Serializer;

public class UserStringSerializer implements Serializer<User, String> {
    @Override
    public String serialize(User src) {
        return new Gson().toJson(src);
    }

    @Override
    public User deserialize(String src) {
        return new Gson().fromJson(src, User.class);
    }
}
