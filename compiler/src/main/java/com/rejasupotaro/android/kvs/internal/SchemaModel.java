package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;
import com.rejasupotaro.android.kvs.internal.exceptions.TableNameIsInvalidException;
import com.squareup.javapoet.ClassName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

public class SchemaModel {
    private String originalClassName;
    private ClassName className;
    private String tableName;
    private List<Field> keys = new ArrayList<>();
    private String builderClassFqcn;

    public String getOriginalClassName() {
        return originalClassName;
    }

    public ClassName getClassName() {
        return className;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Field> getKeys() {
        return keys;
    }

    public String getBuilderClassFqcn() {
        return builderClassFqcn;
    }

    public SchemaModel(TypeElement element, Elements elementUtils) {
        Table table = element.getAnnotation(Table.class);
        this.tableName = table.name();
        String packageName = getPackageName(elementUtils, element);
        this.originalClassName = getClassName(element, packageName);
        if (!originalClassName.endsWith("Schema")) {
            throw new TableNameIsInvalidException(originalClassName + " is invalid. Table class name should end with 'Schema'");
        }
        this.className = ClassName.get(packageName, originalClassName.replace("Schema", ""));
        this.builderClassFqcn = getBuilderFqcn(table);

        findAnnotations(element);
    }

    private void findAnnotations(Element element) {
        for (Element enclosedElement : element.getEnclosedElements()) {
            findAnnotations(enclosedElement);

            Key key = enclosedElement.getAnnotation(Key.class);
            if (key != null) {
                keys.add(new Field(enclosedElement, key));
            }
        }
    }

    private String getPackageName(Elements elementUtils, TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    private static String getBuilderFqcn(Table table) {
        TypeMirror typeMirror = null;
        try {
            table.builder();
        } catch (MirroredTypeException e) { // Throwing this exception is expected.
            typeMirror = e.getTypeMirror();
        }
        assert typeMirror != null;
        return typeMirror.toString();
    }
}
