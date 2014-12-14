package com.rejasupotaro.android.kvs.internal;

import com.rejaupotaro.android.kvs.annotations.Key;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.Arrays;
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
            fqdn.append(model.packageName)
                    .append(".")
                    .append(model.className);

            JavaFileObject sourceFile = filer.createSourceFile(fqdn.toString(), model.element);
            JavaWriter writer = new JavaWriter(sourceFile.openWriter());

            writer.emitPackage(model.packageName);
            writeImports(writer);
            writer.beginType(model.className, "class", EnumSet.of(Modifier.PUBLIC, Modifier.FINAL), model.originalClassName);

            writeFields(writer);
            writeConstructor(writer);
            writeMethods(writer);

            writer.endType();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeImports(JavaWriter writer) throws IOException {
        writer.emitImports(Arrays.asList(
                Classes.CONTEXT));
    }

    private void writeFields(JavaWriter writer) throws IOException {
        writer.emitField("String", "name", EnumSet.of(Modifier.PRIVATE, Modifier.FINAL), "\"" + model.name + "\"");
    }

    private void writeConstructor(JavaWriter writer) throws IOException {
        writer.beginConstructor(EnumSet.of(Modifier.PUBLIC), "Context", "context")
                .emitStatement("init(context, name)")
                .endConstructor();
    }

    private void writeMethods(JavaWriter writer) throws IOException {
        for (VariableElement element : model.keys) {
            Key key = element.getAnnotation(Key.class);
            writeMethod(writer, key, element);
        }
    }

    private void writeMethod(JavaWriter writer, Key key, VariableElement element) throws IOException {
        String fieldTypeFqdn = element.asType().toString();
        String fieldName = element.getSimpleName().toString();
        String keyName = key.value();
        switch (fieldTypeFqdn) {
            case Classes.STRING:
                writeGetter(writer, "String", fieldName, keyName);
                writeSetter(writer, "String", fieldName, keyName);
                writeHas(writer, fieldName, keyName);
                writeRemove(writer, fieldName, keyName);
                break;
            case "int":
                writeGetter(writer, "int", fieldName, keyName);
                writeSetter(writer, "int", fieldName, keyName);
                writeHas(writer, fieldName, keyName);
                writeRemove(writer, fieldName, keyName);
                break;
            default:
                throw new IllegalArgumentException(fieldTypeFqdn + " is not supported");
        }
    }

    private void writeGetter(JavaWriter writer, String fieldTypeName, String fieldName, String keyName) throws IOException {
        writer.beginMethod(fieldTypeName, "get" + StringUtils.capitalize(fieldName), EnumSet.of(Modifier.PUBLIC))
                .emitStatement("return get%s(\"%s\", %s)", StringUtils.capitalize(fieldTypeName), keyName, fieldName)
                .endMethod();
    }

    private void writeSetter(JavaWriter writer, String fieldTypeName, String fieldName, String keyName) throws IOException {
        writer.beginMethod("void", "put" + StringUtils.capitalize(fieldName), EnumSet.of(Modifier.PUBLIC), fieldTypeName, fieldName)
                .emitStatement("put%s(\"%s\", %s)", StringUtils.capitalize(fieldTypeName), keyName, fieldName)
                .endMethod();
    }

    private void writeHas(JavaWriter writer, String fieldName, String keyName) throws IOException {
        writer.beginMethod("boolean", "has" + StringUtils.capitalize(fieldName), EnumSet.of(Modifier.PUBLIC))
                .emitStatement("return has(\"%s\")", keyName)
                .endMethod();
    }

    private void writeRemove(JavaWriter writer, String fieldName, String keyName) throws IOException {
        writer.beginMethod("void", "remove" + StringUtils.capitalize(fieldName), EnumSet.of(Modifier.PUBLIC))
                .emitStatement("remove(\"%s\")", keyName)
                .endMethod();
    }
}
