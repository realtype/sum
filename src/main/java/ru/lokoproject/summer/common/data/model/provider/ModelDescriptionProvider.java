package ru.lokoproject.summer.common.data.model.provider;

import ru.lokoproject.summer.common.data.model.ModelDescription;

public interface ModelDescriptionProvider {
    ModelDescription getModelDescription(String className) throws ModelDescriptionProviderException;
}
