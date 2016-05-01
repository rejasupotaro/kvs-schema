package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.serializers.DefaultPrefsSerializer;
import com.rejasupotaro.android.kvs.serializers.PrefsSerializer;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

public class Field {
    private String prefKeyName;
    private TypeName serializerType;
    private TypeName serializeType;
    private TypeName fieldType;
    private String name;
    private Object value;

    public String getPrefKeyName() {
        return prefKeyName;
    }

    public boolean hasSerializer() {
        return !TypeName.get(DefaultPrefsSerializer.class).equals(serializerType);
    }

    public TypeName getSerializerType() {
        return serializerType;
    }

    public TypeName getSerializeType() {
        return serializeType;
    }

    public TypeName getFieldType() {
        return fieldType;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public Field(Element element, Key key) {
        this.prefKeyName = key.name();

        try {
            Class clazz = key.serializer();
            serializerType = TypeName.get(clazz);
            serializeType = detectSerializeTypeNameByClass(clazz);
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            serializerType = TypeName.get(classTypeMirror);
            serializeType = detectSerializeTypeByTypeElement(classTypeElement);
        }

        VariableElement variable = (VariableElement) element;
        this.fieldType = TypeName.get(element.asType());
        this.name = element.getSimpleName().toString();
        this.value = variable.getConstantValue();
    }

    private TypeName detectSerializeTypeNameByClass(Class clazz) {
        TypeName serializeType = TypeName.get(TypeUtils.getGenericsTypesByClass(clazz, PrefsSerializer.class)[0]);
        return TypeUtils.unbox(serializeType);
    }

    private TypeName detectSerializeTypeByTypeElement(TypeElement element) {
        TypeName serializeType = TypeName.get(TypeUtils.getGenericsTypesByTypeElement(element, PrefsSerializer.class).get(0));
        return TypeUtils.unbox(serializeType);
    }
}
