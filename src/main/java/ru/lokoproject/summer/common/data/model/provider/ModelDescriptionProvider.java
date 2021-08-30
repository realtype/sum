package ru.lokoproject.summer.common.data.model.provider;

import ru.lokoproject.summer.common.data.model.ModelDescription;

import java.util.Map;

public interface ModelDescriptionProvider {

    String MODEL_LEVEL = "modelLevel";
    String CURRENT_MODEL_LEVEL = "currentModelLevel";

    ModelDescription getModelDescription(String className, Map<String, Object> params) throws ModelDescriptionProviderException;

    ModelDescription getModelDescription(String className) throws ModelDescriptionProviderException;
}
