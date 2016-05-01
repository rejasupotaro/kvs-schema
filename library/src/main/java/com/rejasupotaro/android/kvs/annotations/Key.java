package com.rejasupotaro.android.kvs.annotations;

import com.rejasupotaro.android.kvs.serializers.DefaultPrefsSerializer;
import com.rejasupotaro.android.kvs.serializers.PrefsSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Key {
    String name();
    Class<? extends PrefsSerializer> serializer() default DefaultPrefsSerializer.class;
}
