package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

public class Field {
    private String prefKeyName;
    private TypeName type;
    private String name;
    private Object value;

    public String getPrefKeyName() {
        return prefKeyName;
    }

    public TypeName getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public Field(Element element, Key prefKeyName) {
        this.prefKeyName = prefKeyName.value();
        VariableElement variable = (VariableElement) element;
        this.type = TypeName.get(element.asType());
        this.name = element.getSimpleName().toString();
        this.value = variable.getConstantValue();
    }
}
