package com.rejasupotaro.android.kvs.serializers;

public class DefaultPrefsSerializer implements PrefsSerializer<Object, Object> {
    @Override
    public Object serialize(Object src) {
        return src;
    }

    @Override
    public Object deserialize(Object src) {
        return src;
    }
}
