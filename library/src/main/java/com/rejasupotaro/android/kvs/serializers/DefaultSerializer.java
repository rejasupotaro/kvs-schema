package com.rejasupotaro.android.kvs.serializers;

public class DefaultSerializer implements Serializer<Object, Object> {
    @Override
    public Object serialize(Object src) {
        return src;
    }

    @Override
    public Object deserialize(Object src) {
        return src;
    }
}
