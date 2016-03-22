package com.rejasupotaro.android.kvs.internal;

import com.rejasupotaro.android.kvs.annotations.Table;
import com.rejasupotaro.android.kvs.internal.exceptions.TableNameDuplicateException;
import com.rejasupotaro.android.kvs.internal.exceptions.TableNameIsNotDefinedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static com.rejasupotaro.android.kvs.internal.StringUtils.isEmpty;

public final class EnvParser {
    public static List<SchemaModel> parse(RoundEnvironment env, Elements elementUtils) {
        List<SchemaModel> models = new ArrayList<>(env.getElementsAnnotatedWith(Table.class))
                .stream()
                .map(element -> new SchemaModel((TypeElement) element, elementUtils))
                .collect(Collectors.toList());
        validateSchemaModel(models);
        return models;
    }

    public static void validateSchemaModel(List<SchemaModel> models) {
        Set<String> tableNames = new HashSet<>();
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
