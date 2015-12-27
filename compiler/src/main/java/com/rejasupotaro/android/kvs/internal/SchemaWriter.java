package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

public class SchemaWriter {
    private SchemaModel model;

    public SchemaWriter(SchemaModel model) {
        this.model = model;
    }

    public void write(Filer filer) throws IOException {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(model.getClassName());
        classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        ClassName superClassName = ClassName.get(model.getPackageName(), model.getOriginalClassName());
        classBuilder.superclass(superClassName);

        List<FieldSpec> fieldSpecs = createFields(model.getTableName());
        classBuilder.addFields(fieldSpecs);

        List<MethodSpec> methodSpecs = new ArrayList<>();
        methodSpecs.addAll(createConstructors());
        methodSpecs.addAll(createMethods(model.getKeys()));
        classBuilder.addMethods(methodSpecs);

        TypeSpec outClass = classBuilder.build();

        JavaFile.builder(model.getPackageName(), outClass)
                .build()
                .writeTo(filer);
    }

    private static List<FieldSpec> createFields(String tableName) {
        List<FieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(FieldSpec.builder(String.class, "TABLE_NAME", Modifier.PUBLIC, Modifier.FINAL)
                .initializer("$S", tableName)
                .build());
        return fieldSpecs;
    }

    private static List<MethodSpec> createConstructors() {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        methodSpecs.add(MethodSpec.constructorBuilder()
                .addParameter(ClassName.get("android.content", "Context"), "context")
                .addStatement("init(context, TABLE_NAME)")
                .build());
        methodSpecs.add(MethodSpec.constructorBuilder()
                .addParameter(ClassName.get("android.content", "SharedPreferences"), "prefs")
                .addStatement("init(prefs)")
                .build());
        return methodSpecs;
    }

    private List<MethodSpec> createMethods(List<VariableElement> keys) {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        for (VariableElement element : keys) {
            Key key = element.getAnnotation(Key.class);
            methodSpecs.addAll(createMethod(key, element));
        }
        return methodSpecs;
    }

    private List<MethodSpec> createMethod(Key key, VariableElement element) {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        String fieldName = element.getSimpleName().toString();
        String keyName = key.value();

        TypeName t = TypeName.get(element.asType());
        if (t.equals(TypeName.BOOLEAN)) {
            TypeName typeName = TypeName.BOOLEAN;
            methodSpecs.add(createGetterMethod(typeName, "boolean", keyName, fieldName));
            methodSpecs.add(createSetterMethod(typeName, "boolean", keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else if (t.equals(ClassName.get(String.class))) {
            TypeName typeName = ClassName.get(String.class);
            methodSpecs.add(createGetterMethod(typeName, "String", keyName, fieldName));
            methodSpecs.add(createSetterMethod(typeName, "String", keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else if (t.equals(TypeName.FLOAT)) {
            TypeName typeName = TypeName.FLOAT;
            methodSpecs.add(createGetterMethod(typeName, "float", keyName, fieldName));
            methodSpecs.add(createSetterMethod(typeName, "float", keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else if (t.equals(TypeName.INT)) {
            TypeName typeName = TypeName.INT;
            methodSpecs.add(createGetterMethod(typeName, "int", keyName, fieldName));
            methodSpecs.add(createSetterMethod(typeName, "int", keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else if (t.equals(TypeName.LONG)) {
            TypeName typeName = TypeName.LONG;
            methodSpecs.add(createGetterMethod(typeName, "long", keyName, fieldName));
            methodSpecs.add(createSetterMethod(typeName, "long", keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else if (t.equals(ParameterizedTypeName.get(Set.class, String.class))) {
            TypeName typeName = ParameterizedTypeName.get(Set.class, String.class);
            methodSpecs.add(createGetterMethod(typeName, "StringSet", keyName, fieldName));
            methodSpecs.add(createSetterMethod(typeName, "StringSet", keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else {
            String fieldTypeFqcn = element.asType().toString();
            throw new IllegalArgumentException(fieldTypeFqcn + " is not supported");
        }

        return methodSpecs;
    }

    private MethodSpec createGetterMethod(TypeName fieldType, String argTypeOfSuperMethod, String keyName, String fieldName) {
        String methodName = "get" + StringUtils.capitalize(fieldName);
        String superMethodName = "get" + StringUtils.capitalize(argTypeOfSuperMethod);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(fieldType)
                .addStatement("return $N($S, $N)", superMethodName, keyName, fieldName)
                .build();
    }

    private MethodSpec createSetterMethod(TypeName fieldType, String argTypeOfSuperMethod, String keyName, String fieldName) {
        String methodName = "put" + StringUtils.capitalize(fieldName);
        String superMethodName = "put" + StringUtils.capitalize(argTypeOfSuperMethod);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(fieldType, fieldName)
                .addStatement("$N($S, $N)", superMethodName, keyName, fieldName)
                .build();
    }

    private MethodSpec createHasMethod(String keyName, String fieldName) {
        String methodName = "has" + StringUtils.capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(boolean.class)
                .addStatement("return has($S)", keyName)
                .build();
    }

    private MethodSpec createRemoveMethod(String keyName, String fieldName) {
        String methodName = "remove" + StringUtils.capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addStatement("remove($S)", keyName)
                .build();
    }
}
