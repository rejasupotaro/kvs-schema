package com.rejasupotaro.android.kvs.internal;

import com.google.auto.service.AutoService;
import com.rejaupotaro.android.kvs.annotations.Table;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@AutoService(Processor.class)
public class SchemaProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new LinkedHashSet<String>() {{
            add(Table.class.getName());
        }};
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
        typeUtils = env.getTypeUtils();
        filer = env.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        List<SchemaModel> models = EnvParser.parse(env, elementUtils);
        for (SchemaModel model : models) {
            SchemaWriter writer = new SchemaWriter(model);
            writer.write(filer);
        }
        return true;
    }
}
