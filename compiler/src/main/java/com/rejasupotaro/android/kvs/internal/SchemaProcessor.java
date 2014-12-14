package com.rejasupotaro.android.kvs.internal;

import com.google.auto.service.AutoService;
import com.rejaupotaro.android.kvs.annotations.Kvs;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
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
            add(Kvs.class.getName());
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
        List<SchemaModel> models = parseKvsAnnotations(env);
        for (SchemaModel model : models) {
            SchemaWriter writer = new SchemaWriter(model);
            writer.write(filer);
        }
        return true;
    }

    private List<SchemaModel> parseKvsAnnotations(RoundEnvironment env) {
        ArrayList<SchemaModel> models = new ArrayList<>();
        ArrayList<Element> elements = new ArrayList<>(env.getElementsAnnotatedWith(Kvs.class));
        for (Element element : elements) {
            models.add(new SchemaModel((TypeElement) element, elementUtils));
        }
        return models;
    }
}
