package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.annotations.Table;
import com.rejasupotaro.android.kvs.internal.exceptions.TableNameDuplicateException;
import com.rejasupotaro.android.kvs.internal.exceptions.TableNameIsNotDefinedException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static com.rejasupotaro.android.kvs.internal.StringUtils.isEmpty;

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
        List<String> tableNames = new ArrayList<>();
        for (SchemaModel model : models) {
            String tableName = model.getTableName();

            if (isEmpty(tableName)) {
                String originalClassName = model.getOriginalClassName();
                throw new TableNameIsNotDefinedException(originalClassName + " should define table name");
            }

            if (tableNames.contains(tableName)) {
                throw new TableNameDuplicateException("table name \"" + tableName + "\" is already defined");
            }

            tableNames.add(tableName);
        }
    }
}
