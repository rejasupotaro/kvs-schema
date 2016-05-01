package com.rejasupotaro.android.kvs.serializers;

public interface PrefsSerializer<A, B> {
    B serialize(A src);
    A deserialize(B src);
}
