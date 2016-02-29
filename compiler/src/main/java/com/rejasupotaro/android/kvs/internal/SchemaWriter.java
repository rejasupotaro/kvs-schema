package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.PrefsSchema;
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
import java.util.Collection;
import java.util.HashSet;
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

    public void write(Filer filer) throws IOException, ClassNotFoundException {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(model.getClassName().simpleName());
        classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        ClassName superClassName = ClassName.get(PrefsSchema.class);
        classBuilder.superclass(superClassName);

        List<FieldSpec> fieldSpecs = createFields();
        classBuilder.addFields(fieldSpecs);

        List<MethodSpec> methodSpecs = new ArrayList<>();
        methodSpecs.addAll(createConstructors());
        methodSpecs.add(createInitializeMethod());
        methodSpecs.addAll(createMethods());
        classBuilder.addMethods(methodSpecs);

        TypeSpec outClass = classBuilder.build();

        JavaFile.builder(model.getClassName().packageName(), outClass)
                .build()
                .writeTo(filer);
    }

    private List<FieldSpec> createFields() throws ClassNotFoundException {
        List<FieldSpec> fieldSpecs = new ArrayList<>();

        fieldSpecs.add(FieldSpec.builder(String.class, "TABLE_NAME", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", model.getTableName())
                .build());

        fieldSpecs.add(FieldSpec.builder(model.getClassName(), "singleton", Modifier.PRIVATE, Modifier.STATIC)
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

    private MethodSpec createInitializeMethod() {
        return MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.SYNCHRONIZED)
                .returns(model.getClassName())
                .addParameter(ClassName.get("android.content", "Context"), "context")
                .addStatement("if (singleton != null) return singleton")
                .addStatement("synchronized ($N.class) { if (singleton == null) singleton = new $N(context); }",
                        model.getClassName().simpleName(),
                        model.getClassName().simpleName())
                .addStatement("return singleton")
                .build();
    }

    private List<MethodSpec> createMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        for (VariableElement element : model.getKeys()) {
            Key key = element.getAnnotation(Key.class);
            methodSpecs.addAll(createMethods(key, element));
        }
        return methodSpecs;
    }

    private List<MethodSpec> createMethods(Key key, VariableElement element) {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        String fieldName = element.getSimpleName().toString();
        String keyName = key.value();

        TypeName t = TypeName.get(element.asType());
        if (t.equals(TypeName.BOOLEAN)) {
            TypeName fieldType = TypeName.BOOLEAN;
            String argTypeOfSuperMethod = "boolean";

            methodSpecs.add(createGetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.add(createGetterWithDefaultValueMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName, false));
            methodSpecs.addAll(createSetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else if (t.equals(ClassName.get(String.class))) {
            TypeName fieldType = ClassName.get(String.class);
            String argTypeOfSuperMethod = "String";

            String methodName = "get" + StringUtils.capitalize(fieldName);
            String superMethodName = "get" + StringUtils.capitalize(argTypeOfSuperMethod);
            methodSpecs.add(MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(fieldType)
                    .addStatement("return $N($S, \"\")", superMethodName, keyName)
                    .build());

            methodSpecs.add(createGetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.addAll(createSetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else if (t.equals(TypeName.FLOAT)) {
            TypeName fieldType = TypeName.FLOAT;
            String argTypeOfSuperMethod = "float";

            methodSpecs.add(createGetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.add(createGetterWithDefaultValueMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName, 0f));
            methodSpecs.addAll(createSetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else if (t.equals(TypeName.INT)) {
            TypeName fieldType = TypeName.INT;
            String argTypeOfSuperMethod = "int";

            methodSpecs.add(createGetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.add(createGetterWithDefaultValueMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName, 0));
            methodSpecs.addAll(createSetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else if (t.equals(TypeName.LONG)) {
            TypeName fieldType = TypeName.LONG;
            String argTypeOfSuperMethod = "long";

            methodSpecs.add(createGetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.add(createGetterWithDefaultValueMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName, 0L));
            methodSpecs.addAll(createSetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.add(createHasMethod(keyName, fieldName));
            methodSpecs.add(createRemoveMethod(keyName, fieldName));
        } else if (t.equals(ParameterizedTypeName.get(Set.class, String.class))) {
            TypeName fieldType = ParameterizedTypeName.get(Set.class, String.class);
            String argTypeOfSuperMethod = "StringSet";

            String methodName = "get" + StringUtils.capitalize(fieldName);
            String superMethodName = "get" + StringUtils.capitalize(argTypeOfSuperMethod);
            methodSpecs.add(MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(fieldType)
                    .addStatement("return $N($S, new $T<String>())", superMethodName, keyName, ClassName.get(HashSet.class))
                    .build());

            methodSpecs.add(createGetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
            methodSpecs.addAll(createSetterMethod(fieldType, argTypeOfSuperMethod, keyName, fieldName));
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
        String parameterName = "defaultValue";
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(fieldType, parameterName)
                .returns(fieldType)
                .addStatement("return $N($S, $N)", superMethodName, keyName, parameterName)
                .build();
    }

    private MethodSpec createGetterWithDefaultValueMethod(TypeName fieldType, String argTypeOfSuperMethod, String keyName, String fieldName, Object defaultValue) {
        String methodName = "get" + StringUtils.capitalize(fieldName);
        String superMethodName = "get" + StringUtils.capitalize(argTypeOfSuperMethod);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(fieldType)
                .addStatement("return $N($S, $L)", superMethodName, keyName, defaultValue)
                .build();
    }

    private Collection<MethodSpec> createSetterMethod(TypeName fieldType, String argTypeOfSuperMethod, String keyName, String fieldName) {
        ArrayList<MethodSpec> methodSpecs = new ArrayList<>();

        {
            String methodName = "set" + StringUtils.capitalize(fieldName);
            String superMethodName = "put" + StringUtils.capitalize(argTypeOfSuperMethod);
            methodSpecs.add(MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(fieldType, fieldName)
                    .addStatement("$N($S, $N)", superMethodName, keyName, fieldName)
                    .build());
        }

        {
            String methodName = "put" + StringUtils.capitalize(fieldName);
            String superMethodName = "put" + StringUtils.capitalize(argTypeOfSuperMethod);
            methodSpecs.add(MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(fieldType, fieldName)
                    .addStatement("$N($S, $N)", superMethodName, keyName, fieldName)
                    .build());
        }

        return methodSpecs;
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
