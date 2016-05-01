package com.rejasupotaro.android.kvs.annotations;

import com.rejasupotaro.android.kvs.serializers.DefaultSerializer;
import com.rejasupotaro.android.kvs.serializers.Serializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Key {
    String name();
    Class<? extends Serializer> serializer() default DefaultSerializer.class;
}
