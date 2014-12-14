package com.rejasupotaro.android.kvs.internal;

import com.rejaupotaro.android.kvs.annotations.Key;
import com.rejaupotaro.android.kvs.annotations.Kvs;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class KvsModel {
    public TypeElement element;
    public String packageName;
    public String originalClassName;
    public String className;
    public String name;
    public List<VariableElement> keys = new ArrayList<>();

    public KvsModel(TypeElement element, Elements elementUtils) {
        this.element = element;
        Kvs kvs = element.getAnnotation(Kvs.class);
        this.name = kvs.name();
        this.packageName = getPackageName(elementUtils, element);
        this.originalClassName = getClassName(element, packageName);
        this.className = originalClassName.replace("Schema", "");

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
