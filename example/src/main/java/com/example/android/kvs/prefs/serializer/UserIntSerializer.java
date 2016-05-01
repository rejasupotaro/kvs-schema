package com.example.android.kvs.prefs.serializer;

import com.example.android.kvs.models.User;
import com.rejasupotaro.android.kvs.serializers.PrefsSerializer;

import java.util.ArrayList;
import java.util.List;

public class UserIntSerializer implements PrefsSerializer<User, Integer> {
    public static final List<User> USERS = new ArrayList<User>() {{
        add(new User(1, "Smith", 32, true));
        add(new User(2, "John", 28, true));
        add(new User(3, "Robert", 24, false));
    }};

    @Override
    public Integer serialize(User src) {
        int index = -1;
        for (int i = 0; i < USERS.size(); i++) {
            if (USERS.get(i).getName().equals(src.getName())) {
                index = i;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException("Given user does not exist.");
        }
        return index;
    }

    @Override
    public User deserialize(Integer src) {
        return USERS.get(src);
    }
}
