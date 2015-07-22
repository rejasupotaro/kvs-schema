package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        for (FieldSpec fieldSpec : fieldSpecs) {
            classBuilder.addField(fieldSpec);
        }

        List<MethodSpec> methodSpecs = new ArrayList<>();
        methodSpecs.addAll(createConstructors());
        methodSpecs.addAll(createMethods());
        for (MethodSpec methodSpec : methodSpecs) {
            classBuilder.addMethod(methodSpec);
        }

        TypeSpec outClass = classBuilder.build();

        JavaFile.builder(model.getPackageName(), outClass)
                .build()
                .writeTo(filer);
    }

    private List<FieldSpec> createFields(String tableName) {
        List<FieldSpec> fieldSpecs = new ArrayList<>();
        fieldSpecs.add(FieldSpec.builder(String.class, "TABLE_NAME", Modifier.PUBLIC, Modifier.FINAL)
                .initializer("$S", tableName)
                .build());
        return fieldSpecs;
    }

    private List<MethodSpec> createConstructors() {
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

    private List<MethodSpec> createMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        for (VariableElement element : model.getKeys()) {
            Key key = element.getAnnotation(Key.class);
            methodSpecs.addAll(createMethod(key, element));
        }
        return methodSpecs;
    }

    private List<MethodSpec> createMethod(Key key, VariableElement element) {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        String fieldTypeFqcn = element.asType().toString();
        String fieldName = element.getSimpleName().toString();
        String keyName = key.value();
        switch (fieldTypeFqcn) {
            case "boolean":
                methodSpecs.add(createGetterMethod(boolean.class, "boolean", fieldName, keyName));
                methodSpecs.add(createSetterMethod(boolean.class, "boolean", fieldName, keyName));
                methodSpecs.add(createHasMethod(fieldName, keyName));
                methodSpecs.add(createRemoveMethod(fieldName, keyName));
                break;
            case Classes.STRING:
                methodSpecs.add(createGetterMethod(String.class, "String", fieldName, keyName));
                methodSpecs.add(createSetterMethod(String.class, "String", fieldName, keyName));
                methodSpecs.add(createHasMethod(fieldName, keyName));
                methodSpecs.add(createRemoveMethod(fieldName, keyName));
                break;
            case "float":
                methodSpecs.add(createGetterMethod(float.class, "float", fieldName, keyName));
                methodSpecs.add(createSetterMethod(float.class, "float", fieldName, keyName));
                methodSpecs.add(createHasMethod(fieldName, keyName));
                methodSpecs.add(createRemoveMethod(fieldName, keyName));
                break;
            case "int":
                methodSpecs.add(createGetterMethod(int.class, "int", fieldName, keyName));
                methodSpecs.add(createSetterMethod(int.class, "int", fieldName, keyName));
                methodSpecs.add(createHasMethod(fieldName, keyName));
                methodSpecs.add(createRemoveMethod(fieldName, keyName));
                break;
            case "long":
                methodSpecs.add(createGetterMethod(long.class, "long", fieldName, keyName));
                methodSpecs.add(createSetterMethod(long.class, "long", fieldName, keyName));
                methodSpecs.add(createHasMethod(fieldName, keyName));
                methodSpecs.add(createRemoveMethod(fieldName, keyName));
                break;
            default:
                throw new IllegalArgumentException(fieldTypeFqcn + " is not supported");
        }
        return methodSpecs;
    }

    private MethodSpec createGetterMethod(Type fieldType, String argTypeOfSuperMethod, String fieldName, String keyName) {
        String methodName = "get" + StringUtils.capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(fieldType)
                .addStatement("return $N($S, $N)", "get" + StringUtils.capitalize(argTypeOfSuperMethod), keyName, fieldName)
                .build();
    }

    private MethodSpec createSetterMethod(Type fieldType, String argTypeOfSuperMethod, String fieldName, String keyName) {
        String methodName = "put" + StringUtils.capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(fieldType, fieldName)
                .addStatement("$N($S, $N)", "put" + StringUtils.capitalize(argTypeOfSuperMethod), keyName, fieldName)
                .build();
    }

    private MethodSpec createHasMethod(String fieldName, String keyName) {
        String methodName = "has" + StringUtils.capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(boolean.class)
                .addStatement("return has($S)", keyName)
                .build();
    }

    private MethodSpec createRemoveMethod(String fieldName, String keyName) {
        String methodName = "remove" + StringUtils.capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addStatement("remove($S)", keyName)
                .build();
    }
}
