package com.rejasupotaro.android.kvs.internal;

import org.junit.Test;

import java.lang.reflect.Type;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeUtilsTest {
    @Test
    public void getGenericsTypesByClass() {
        Class<? extends Serializer> clazz = SerializerImpl.class;
        Type[] types = TypeUtils.getGenericsTypesByClass(clazz, Serializer.class);
        assertThat(types.length).isEqualTo(2);
        assertThat(types[0].getTypeName()).isEqualTo("java.lang.String");
        assertThat(types[1].getTypeName()).isEqualTo("java.lang.Integer");
    }

    private interface Serializer<A, B> {
    }

    private class SerializerImpl implements Serializer<String, Integer> {
    }
}
