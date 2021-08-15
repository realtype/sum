package ru.lokoproject.summer.common.data.model.provider;

import ru.lokoproject.summer.common.data.model.FieldDescription;
import ru.lokoproject.summer.common.data.model.ModelDescription;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaClassModelDescriptionProvider implements ModelDescriptionProvider {

    ModelDescriptionProvider rootProvider;

    public ModelDescription getModelDescription(String className) throws ModelDescriptionProviderException {
        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            // TODO: 13.08.2021 logging
            throw new ModelDescriptionProviderException(e);
        }

        getFields(clazz).stream().map(field -> {
            FieldDescription fieldDescription;
            if (isFieldCollection(field)) {
                fieldDescription

            } else if (isFieldMap(field)) {

            } else if (isFieldPrimitive(field)) {

            } else {

            }
            return fieldDescription;
        }).collect(Collectors.toList());

        return null;
    }

    private boolean isFieldPrimitive(Field field) {
        return false;
    }

    private boolean isFieldMap(Field field) {
        return false;
    }

    private boolean isFieldCollection(Field field) {
        return false;
    }

    private List<Field> getFields(Class clazz) {
        List<Field> fields = new ArrayList();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
