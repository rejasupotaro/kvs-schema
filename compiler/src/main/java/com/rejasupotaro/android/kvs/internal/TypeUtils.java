package com.rejasupotaro.android.kvs.internal;

import com.squareup.javapoet.TypeName;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public final class TypeUtils {
    private TypeUtils() {
    }

    public static TypeName unbox(TypeName typeName) {
        try {
            return typeName.unbox();
        } catch (UnsupportedOperationException ignore) {
            return typeName;
        }
    }

    public static Type[] getGenericsTypesByClass(Class clazz, Type type) {
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

    public static List<? extends TypeMirror> getGenericsTypesByTypeElement(TypeElement element, Class clazz) {
        TypeElement targetType = element;
        while (targetType != null) {
            if (!targetType.getInterfaces().isEmpty()) {
                for (TypeMirror in : targetType.getInterfaces()) {
                    DeclaredType inType = (DeclaredType) in;
                    if (inType.asElement().getSimpleName().toString()
                            .equals(clazz.getSimpleName())) {
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
