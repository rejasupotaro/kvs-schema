package com.rejasupotaro.android.kvs.serializers;

public interface Serializer<A, B> {
    B serialize(A src);
    A deserialize(B src);
}
