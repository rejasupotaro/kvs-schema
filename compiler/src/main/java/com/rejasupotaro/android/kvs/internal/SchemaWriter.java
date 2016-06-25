package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.PrefsSchema;
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

public class SchemaWriter {
    private SchemaModel model;

    public SchemaWriter(SchemaModel model) {
        this.model = model;
    }

    public void write(Filer filer) throws IOException {
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

    private List<FieldSpec> createFields() {
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
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("android.content", "Context"), "context")
                .addStatement("init(context, TABLE_NAME)")
                .build());
        methodSpecs.add(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("android.content", "SharedPreferences"), "prefs")
                .addStatement("init(prefs)")
                .build());
        return methodSpecs;
    }

    private MethodSpec createInitializeMethod() {
        if ("java.lang.Object".equals(model.getBuilderClassFqcn())) {
            return MethodSpec.methodBuilder("get")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(model.getClassName())
                    .addParameter(ClassName.get("android.content", "Context"), "context")
                    .addStatement("if (singleton != null) return singleton")
                    .addStatement("synchronized ($N.class) { if (singleton == null) singleton = new $N(context); }",
                            model.getClassName().simpleName(),
                            model.getClassName().simpleName())
                    .addStatement("return singleton")
                    .build();
        } else {
            return MethodSpec.methodBuilder("get")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(model.getClassName())
                    .addParameter(ClassName.get("android.content", "Context"), "context")
                    .addStatement("if (singleton != null) return singleton")
                    .addStatement("synchronized ($N.class) { if (singleton == null) singleton = new $N().build(context); }",
                            model.getClassName().simpleName(),
                            model.getBuilderClassFqcn())
                    .addStatement("return singleton")
                    .build();
        }
    }

    private List<MethodSpec> createMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        for (Field field : model.getKeys()) {
            methodSpecs.addAll(createMethods(field));
        }
        return methodSpecs;
    }

    private List<MethodSpec> createMethods(Field field) {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        if (TypeName.BOOLEAN.equals(field.getFieldType())) {
            String argTypeOfSuperMethod = "boolean";
            String defaultValue = field.getValue() == null
                    ? "false"
                    : field.getValue().toString();
            methodSpecs.add(createGetterWithDefaultValue(field, argTypeOfSuperMethod));
            methodSpecs.add(createGetter(field, argTypeOfSuperMethod, defaultValue));
            methodSpecs.addAll(createSetter(field, argTypeOfSuperMethod));
            methodSpecs.add(createHasMethod(field));
            methodSpecs.add(createRemoveMethod(field));
        } else if (ClassName.get(String.class).equals(field.getFieldType())) {
            String argTypeOfSuperMethod = "String";
            String defaultValue = field.getValue() == null
                    ? ""
                    : field.getValue().toString();
            String methodName = "get" + StringUtils.capitalize(field.getName());
            String superMethodName = "get" + StringUtils.capitalize(argTypeOfSuperMethod);
            if (field.hasSerializer()) {
                methodSpecs.add(MethodSpec.methodBuilder(methodName)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(field.getSerializeType())
                        .addStatement("return new $T().deserialize($N($S, \"\"))",
                                field.getSerializerType(),
                                superMethodName,
                                field.getPrefKeyName())
                        .build());
            } else {
                methodSpecs.add(MethodSpec.methodBuilder(methodName)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(field.getFieldType())
                        .addStatement("return $N($S, $S)", superMethodName, field.getPrefKeyName(), defaultValue)
                        .build());
            }
            methodSpecs.addAll(createSetter(field, argTypeOfSuperMethod));
            methodSpecs.add(createGetterWithDefaultValue(field, argTypeOfSuperMethod));
            methodSpecs.add(createHasMethod(field));
            methodSpecs.add(createRemoveMethod(field));
        } else if (TypeName.FLOAT.equals(field.getFieldType())) {
            String argTypeOfSuperMethod = "float";
            String defaultValue = field.getValue() == null
                    ? "0.0F"
                    : field.getValue().toString() + "f";
            methodSpecs.add(createGetterWithDefaultValue(field, argTypeOfSuperMethod));
            methodSpecs.add(createGetter(field, argTypeOfSuperMethod, defaultValue));
            methodSpecs.addAll(createSetter(field, argTypeOfSuperMethod));
            methodSpecs.add(createHasMethod(field));
            methodSpecs.add(createRemoveMethod(field));
        } else if (TypeName.INT.equals(field.getFieldType())) {
            String argTypeOfSuperMethod = "int";
            String defaultValue = field.getValue() == null
                    ? "0"
                    : field.getValue().toString();
            methodSpecs.add(createGetterWithDefaultValue(field, argTypeOfSuperMethod));
            methodSpecs.add(createGetter(field, argTypeOfSuperMethod, defaultValue));
            methodSpecs.addAll(createSetter(field, argTypeOfSuperMethod));
            methodSpecs.add(createHasMethod(field));
            methodSpecs.add(createRemoveMethod(field));
        } else if (TypeName.LONG.equals(field.getFieldType())) {
            String argTypeOfSuperMethod = "long";
            String defaultValue = field.getValue() == null
                    ? "0L"
                    : field.getValue().toString() + "L";
            methodSpecs.add(createGetterWithDefaultValue(field, argTypeOfSuperMethod));
            methodSpecs.add(createGetter(field, argTypeOfSuperMethod, defaultValue));
            methodSpecs.addAll(createSetter(field, argTypeOfSuperMethod));
            methodSpecs.add(createHasMethod(field));
            methodSpecs.add(createRemoveMethod(field));
        } else if (ParameterizedTypeName.get(Set.class, String.class).equals(field.getFieldType())) {
            String argTypeOfSuperMethod = "StringSet";
            String methodName = "get" + StringUtils.capitalize(field.getName());
            String superMethodName = "get" + StringUtils.capitalize(argTypeOfSuperMethod);

            methodSpecs.add(MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(field.getFieldType())
                    .addStatement("return $N($S, new $T<String>())", superMethodName, field.getPrefKeyName(), ClassName.get(HashSet.class))
                    .build());
            methodSpecs.add(createGetterWithDefaultValue(field, argTypeOfSuperMethod));
            methodSpecs.addAll(createSetter(field, argTypeOfSuperMethod));
            methodSpecs.add(createHasMethod(field));
            methodSpecs.add(createRemoveMethod(field));
        } else {
            throw new IllegalArgumentException(field.getFieldType() + " is not supported");
        }

        return methodSpecs;
    }

    private MethodSpec createGetterWithDefaultValue(Field field, String argTypeOfSuperMethod) {
        String methodName = "get" + StringUtils.capitalize(field.getName());
        String superMethodName = "get" + StringUtils.capitalize(argTypeOfSuperMethod);
        String parameterName = "defValue";
        if (field.hasSerializer()) {
            return MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(field.getFieldType(), parameterName)
                    .returns(field.getSerializeType())
                    .addStatement("return new $T().deserialize($N($S, $L))",
                            field.getSerializerType(),
                            superMethodName,
                            field.getPrefKeyName(),
                            parameterName)
                    .build();
        } else {
            return MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(field.getFieldType(), parameterName)
                    .returns(field.getFieldType())
                    .addStatement("return $N($S, $N)", superMethodName, field.getPrefKeyName(), parameterName)
                    .build();
        }
    }

    private MethodSpec createGetter(Field field, String argTypeOfSuperMethod, String defaultValue) {
        String methodName = "get" + StringUtils.capitalize(field.getName());
        String superMethodName = "get" + StringUtils.capitalize(argTypeOfSuperMethod);
        if (field.hasSerializer()) {
            return MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(field.getSerializeType())
                    .addStatement("return new $T().deserialize($N($S, $L))",
                            field.getSerializerType(),
                            superMethodName,
                            field.getPrefKeyName(),
                            defaultValue)
                    .build();
        } else {
            return MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(field.getFieldType())
                    .addStatement("return $N($S, $L)", superMethodName, field.getPrefKeyName(), defaultValue)
                    .build();
        }
    }

    private Collection<MethodSpec> createSetter(Field field, String argTypeOfSuperMethod) {
        ArrayList<MethodSpec> methodSpecs = new ArrayList<>();
        {
            String methodName = "set" + StringUtils.capitalize(field.getName());
            String superMethodName = "put" + StringUtils.capitalize(argTypeOfSuperMethod);
            if (field.hasSerializer()) {
                methodSpecs.add(MethodSpec.methodBuilder(methodName)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addParameter(field.getSerializeType(), field.getName())
                        .addStatement("$N($S, new $T().serialize($N))",
                                superMethodName,
                                field.getPrefKeyName(),
                                field.getSerializerType(),
                                field.getName())
                        .build());
            } else {
                methodSpecs.add(MethodSpec.methodBuilder(methodName)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addParameter(field.getFieldType(), field.getName())
                        .addStatement("$N($S, $N)", superMethodName, field.getPrefKeyName(), field.getName())
                        .build());
            }
        }

        {
            String methodName = "put" + StringUtils.capitalize(field.getName());
            String superMethodName = "put" + StringUtils.capitalize(argTypeOfSuperMethod);
            if (field.hasSerializer()) {
                methodSpecs.add(MethodSpec.methodBuilder(methodName)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addParameter(field.getSerializeType(), field.getName())
                        .addStatement("$N($S, new $T().serialize($N))",
                                superMethodName,
                                field.getPrefKeyName(),
                                field.getSerializerType(),
                                field.getName())
                        .build());
            } else {
                methodSpecs.add(MethodSpec.methodBuilder(methodName)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addParameter(field.getFieldType(), field.getName())
                        .addStatement("$N($S, $N)", superMethodName, field.getPrefKeyName(), field.getName())
                        .build());
            }
        }

        return methodSpecs;
    }

    private MethodSpec createHasMethod(Field field) {
        String methodName = "has" + StringUtils.capitalize(field.getName());
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(boolean.class)
                .addStatement("return has($S)", field.getPrefKeyName())
                .build();
    }

    private MethodSpec createRemoveMethod(Field field) {
        String methodName = "remove" + StringUtils.capitalize(field.getName());
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addStatement("remove($S)", field.getPrefKeyName())
                .build();
    }
}
