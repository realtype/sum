package ru.lokoproject.summer.common.data.model.provider;

import lombok.RequiredArgsConstructor;
import ru.lokoproject.summer.common.data.model.FieldDescription;
import ru.lokoproject.summer.common.data.model.ModelDescription;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
public class JavaClassModelDescriptionProvider extends GeneralModelDescriptionProvider {

    private static final List<Class> PRIMITIVE_TYPES = Arrays.asList(
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Short.class,
            Byte.class,
            Boolean.class,
            String.class
    );

    private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = Map.of(
            boolean.class, Boolean.class,
            byte.class, Byte.class,
            char.class, Character.class,
            double.class, Double.class,
            float.class, Float.class,
            int.class, Integer.class,
            long.class, Long.class,
            short.class, Short.class
    );

    protected ModelDescription getModelDescription(String className, Map<String, Object> params, int currentModelLevel) throws ModelDescriptionProviderException {
        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            // TODO: 13.08.2021 logging
            throw new ModelDescriptionProviderException(e);
        }

        List<FieldDescription> fieldDescriptions = getFields(clazz).stream().map(field -> {
            FieldDescription fieldDescription = new FieldDescription();
            fieldDescription.setName(field.getName());

            Class fieldType = field.getType();
            if (isFieldCollection(field)) {
                fieldType = (Class) (((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
                fieldDescription.setIsCollection(true);

            } else if (isFieldMap(field)) {

            }

            if (isFieldPrimitive(fieldType)) {
                fieldDescription.setIsPrimitive(true);
                fieldDescription.setType(getPrimitiveTypeName(fieldType));
            } else {
                fieldDescription.setType(fieldType.getCanonicalName());
                fieldDescription.setModelDescription(
                        getDeeperModelDescription(
                                fieldType.getCanonicalName(),
                                params,
                                getModelLevel(params),
                                currentModelLevel)
                );
            }

            return fieldDescription;
        }).collect(Collectors.toList());

        ModelDescription modelDescription = new ModelDescription();
        modelDescription.setClassName(className);
        modelDescription.setFieldDescriptions(fieldDescriptions);

        return modelDescription;
    }

    private String getPrimitiveTypeName(Class fieldType) {
        if (fieldType.isPrimitive()) {
            fieldType = PRIMITIVES_TO_WRAPPERS.get(fieldType);
        }
        if (!PRIMITIVE_TYPES.contains(fieldType))
            throw new IllegalArgumentException("fieldType must be in PRIMITIVE_TYPES list");
        return fieldType.getSimpleName();
    }

    private boolean isFieldPrimitive(Class fieldType) {
        return PRIMITIVE_TYPES.contains(fieldType) || fieldType.isPrimitive();
    }

    private boolean isFieldMap(Field field) {
        return Map.class.isAssignableFrom(field.getDeclaringClass());
    }

    private boolean isFieldCollection(Field field) {
        return Collection.class.isAssignableFrom(field.getDeclaringClass());
    }

    private List<Field> getFields(Class clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
