package com.rejasupotaro.android.kvs.internal;

import com.rejaupotaro.android.kvs.annotations.Table;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public final class EnvParser {
    public static List<SchemaModel> parse(RoundEnvironment env, Elements elementUtils) {
        ArrayList<SchemaModel> models = new ArrayList<>();
        ArrayList<Element> elements = new ArrayList<>(env.getElementsAnnotatedWith(Table.class));
        for (Element element : elements) {
            SchemaModel model = new SchemaModel((TypeElement) element, elementUtils);
            models.add(model);
        }
        validateSchemaModel(models);
        return models;
    }

    public static void validateSchemaModel(List<SchemaModel> models) {
        for (SchemaModel model : models) {
            if (model.getTableName() == null || model.getTableName().equals("")) {
                String originalClassName = model.getOriginalClassName();
                throw new IllegalArgumentException(originalClassName + " should define table name");
            }
        }
    }
}
