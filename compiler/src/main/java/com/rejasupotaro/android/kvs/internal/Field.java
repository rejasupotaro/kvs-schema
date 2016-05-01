package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.serializers.DefaultPrefsSerializer;
import com.rejasupotaro.android.kvs.serializers.PrefsSerializer;
import com.squareup.javapoet.TypeName;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

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
        TypeName serializeType = TypeName.get(getSerializerGenericsTypesByClass(clazz)[0]);
        try {
            serializeType = serializeType.unbox();
        } catch (UnsupportedOperationException ignore) {
        }
        return serializeType;
    }

    private TypeName detectSerializeTypeByTypeElement(TypeElement element) {
        TypeName serializeType = TypeName.get(getSerializerGenericsTypesByTypeElement(element).get(0));
        try {
            serializeType = serializeType.unbox();
        } catch (UnsupportedOperationException ignore) {
        }
        return serializeType;
    }

    private Type[] getSerializerGenericsTypesByClass(Class clazz) {
        Type type = PrefsSerializer.class;
        Class targetClass = clazz;
        while (targetClass != null) {
            for (Type in : targetClass.getGenericInterfaces()) {
                if (in instanceof ParameterizedType &&
                        type.equals(((ParameterizedType) in).getRawType())) {
                    return ((ParameterizedType) in).getActualTypeArguments();
                }
            }
            Type superClazz = targetClass.getGenericSuperclass();
            if (superClazz instanceof ParameterizedType &&
                    type.equals(((ParameterizedType) superClazz).getRawType())) {
                return ((ParameterizedType) superClazz).getActualTypeArguments();
            }
            targetClass = targetClass.getSuperclass();
        }
        throw new UnsupportedOperationException("Not found serializer type: " + clazz.getName());
    }

    private List<? extends TypeMirror> getSerializerGenericsTypesByTypeElement(TypeElement element) {
        TypeElement targetType = element;
        while (targetType != null) {
            if (!targetType.getInterfaces().isEmpty()) {
                for (TypeMirror in : targetType.getInterfaces()) {
                    DeclaredType inType = (DeclaredType) in;
                    if (inType.asElement().getSimpleName().toString()
                            .equals(PrefsSerializer.class.getSimpleName())) {
                        return inType.getTypeArguments();
                    }
                }
            }

            TypeMirror superClassType = targetType.getSuperclass();
            if (superClassType.getKind().equals(TypeKind.DECLARED)) {
                targetType = (TypeElement) ((DeclaredType) superClassType).asElement();
            } else {
                break;
            }
        }
        throw new UnsupportedOperationException("Not found serializer type: " + element.toString());
    }
}
