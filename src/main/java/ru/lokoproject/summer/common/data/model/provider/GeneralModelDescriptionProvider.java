package ru.lokoproject.summer.common.data.model.provider;

import lombok.Setter;
import ru.lokoproject.summer.common.data.model.ModelDescription;

import java.util.Collections;
import java.util.Map;

@Setter
public abstract class GeneralModelDescriptionProvider implements ModelDescriptionProvider {

    private GeneralModelDescriptionProvider rootProvider;

    public ModelDescription getModelDescription(String className) throws ModelDescriptionProviderException {
        return getModelDescription(className, Collections.emptyMap());
    }

    public ModelDescription getModelDescription(String className, Map<String, Object> params) throws ModelDescriptionProviderException {
        return getModelDescription(className, params, 0);
    }

    protected abstract ModelDescription getModelDescription(String className, Map<String, Object> params, int currentModelLevel);


    protected ModelDescription getDeeperModelDescription(String className, Map<String, Object> params, int modelLevel, int currentModelLevel) throws ModelDescriptionProviderException {
        if (currentModelLevel < modelLevel) {
            return rootProvider.getModelDescription(className, params, ++currentModelLevel);
        } else return null;
    }

    protected int getModelLevel(Map<String, Object> params) {
        Object currentLevel = params.get(MODEL_LEVEL);
        if((currentLevel != null) && (Integer.class.isAssignableFrom(currentLevel.getClass()))){
            return (int) currentLevel;
        }
        return 0;
    }

}
