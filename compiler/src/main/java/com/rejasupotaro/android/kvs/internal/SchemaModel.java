package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;
import com.rejasupotaro.android.kvs.internal.exceptions.TableNameIsInvalidException;
import com.squareup.javapoet.ClassName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class SchemaModel {
    private TypeElement element;
    private String originalClassName;
    private ClassName className;
    private String tableName;
    private List<VariableElement> keys = new ArrayList<>();

    public TypeElement getElement() {
        return element;
    }

    public String getOriginalClassName() {
        return originalClassName;
    }

    public ClassName getClassName() {
        return className;
    }

    public String getTableName() {
        return tableName;
    }

    public List<VariableElement> getKeys() {
        return keys;
    }

    public SchemaModel(TypeElement element, Elements elementUtils) {
        this.element = element;
        Table table = element.getAnnotation(Table.class);
        this.tableName = table.value();
        String packageName = getPackageName(elementUtils, element);
        this.originalClassName = getClassName(element, packageName);
        if (!originalClassName.endsWith("Schema")) {
            throw new TableNameIsInvalidException(originalClassName + " is invalid. Table class name should end with 'Schema'");
        }
        this.className = ClassName.get(packageName, originalClassName.replace("Schema", ""));

        findAnnotations(element);
    }

    private void findAnnotations(Element element) {
        for (Element enclosedElement : element.getEnclosedElements()) {
            findAnnotations(enclosedElement);

            Key key = enclosedElement.getAnnotation(Key.class);
            if (key != null) {
                keys.add((VariableElement) enclosedElement);
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
}
