package com.rejasupotaro.android.kvs.internal;

import com.rejaupotaro.android.kvs.annotations.Key;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

public class SchemaWriter {
    private SchemaModel model;

    public SchemaWriter(SchemaModel model) {
        this.model = model;
    }

    public void write(Filer filer) {
        try {
            StringBuilder fqdn = new StringBuilder();
            fqdn.append(model.getPackageName())
                    .append(".")
                    .append(model.getClassName());

            JavaFileObject sourceFile = filer.createSourceFile(fqdn.toString(), model.getElement());
            JavaWriter writer = new JavaWriter(sourceFile.openWriter());

            writer.emitPackage(model.getPackageName());
            writeImports(writer);
            writer.beginType(model.getClassName(), "class", EnumSet.of(Modifier.PUBLIC, Modifier.FINAL), model.getOriginalClassName());

            writeFields(writer);
            writeConstructors(writer);
            writeMethods(writer);

            writer.endType();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeImports(JavaWriter writer) throws IOException {
        ArrayList<String> imports = new ArrayList<String>() {{
            add(Classes.CONTEXT);
            add(Classes.SHARED_PREFERENCES);
        }};
        for (VariableElement element : model.getKeys()) {
            String fieldTypeFqdn = element.asType().toString();
            if (!imports.contains(fieldTypeFqdn) && fieldTypeFqdn.contains(".")) {
                imports.add(fieldTypeFqdn);
            }
        }
        writer.emitImports(imports);
    }

    private void writeFields(JavaWriter writer) throws IOException {
        writer.emitField("String", "tableName", EnumSet.of(Modifier.PRIVATE, Modifier.FINAL), "\"" + model.getTableName() + "\"");
    }

    private void writeConstructors(JavaWriter writer) throws IOException {
        writer.beginConstructor(EnumSet.of(Modifier.PUBLIC), "Context", "context")
                .emitStatement("init(context, tableName)")
                .endConstructor();

        writer.beginConstructor(EnumSet.of(Modifier.PUBLIC), "SharedPreferences", "prefs")
                .emitStatement("init(prefs)")
                .endConstructor();
    }

    private void writeMethods(JavaWriter writer) throws IOException {
        for (VariableElement element : model.getKeys()) {
            Key key = element.getAnnotation(Key.class);
            writeMethod(writer, key, element);
        }
    }

    private void writeMethod(JavaWriter writer, Key key, VariableElement element) throws IOException {
        String fieldTypeFqdn = element.asType().toString();
        String fieldName = element.getSimpleName().toString();
        String keyName = key.value();
        switch (fieldTypeFqdn) {
            case "boolean":
                writeGetter(writer, "boolean", "boolean", fieldName, keyName);
                writeSetter(writer, "boolean", "boolean", fieldName, keyName);
                writeHas(writer, fieldName, keyName);
                writeRemove(writer, fieldName, keyName);
                break;
            case Classes.STRING:
                writeGetter(writer, "String", "String", fieldName, keyName);
                writeSetter(writer, "String", "String", fieldName, keyName);
                writeHas(writer, fieldName, keyName);
                writeRemove(writer, fieldName, keyName);
                break;
            case "float":
                writeGetter(writer, "float", "float", fieldName, keyName);
                writeSetter(writer, "float", "float", fieldName, keyName);
                writeHas(writer, fieldName, keyName);
                writeRemove(writer, fieldName, keyName);
                break;
            case "int":
                writeGetter(writer, "int", "int", fieldName, keyName);
                writeSetter(writer, "int", "int", fieldName, keyName);
                writeHas(writer, fieldName, keyName);
                writeRemove(writer, fieldName, keyName);
                break;
            case "long":
                writeGetter(writer, "long", "long", fieldName, keyName);
                writeSetter(writer, "long", "long", fieldName, keyName);
                writeHas(writer, fieldName, keyName);
                writeRemove(writer, fieldName, keyName);
                break;
            default:
                throw new IllegalArgumentException(fieldTypeFqdn + " is not supported");
        }
    }

    private void writeGetter(JavaWriter writer, String fieldTypeName, String argTypeOfSuperMethod, String fieldName, String keyName) throws IOException {
        String methodName = "get" + StringUtils.capitalize(fieldName);
        writer.beginMethod(fieldTypeName, methodName, EnumSet.of(Modifier.PUBLIC))
                .emitStatement("return get%s(\"%s\", %s)", StringUtils.capitalize(argTypeOfSuperMethod), keyName, fieldName)
                .endMethod();
    }

    private void writeSetter(JavaWriter writer, String fieldTypeName, String argTypeOfSuperMethod, String fieldName, String keyName) throws IOException {
        String methodName = "put" + StringUtils.capitalize(fieldName);
        writer.beginMethod("void", methodName, EnumSet.of(Modifier.PUBLIC), fieldTypeName, fieldName)
                .emitStatement("put%s(\"%s\", %s)", StringUtils.capitalize(argTypeOfSuperMethod), keyName, fieldName)
                .endMethod();
    }

    private void writeHas(JavaWriter writer, String fieldName, String keyName) throws IOException {
        String methodName = "has" + StringUtils.capitalize(fieldName);
        writer.beginMethod("boolean", methodName, EnumSet.of(Modifier.PUBLIC))
                .emitStatement("return has(\"%s\")", keyName)
                .endMethod();
    }

    private void writeRemove(JavaWriter writer, String fieldName, String keyName) throws IOException {
        String methodName = "remove" + StringUtils.capitalize(fieldName);
        writer.beginMethod("void", methodName, EnumSet.of(Modifier.PUBLIC))
                .emitStatement("remove(\"%s\")", keyName)
                .endMethod();
    }
}
