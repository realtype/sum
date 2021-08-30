package ru.lokoproject.summer.common.data.model.provider;

import org.junit.Test;
import ru.lokoproject.summer.common.data.model.ModelDescription;

import java.util.Map;

import static org.junit.Assert.*;

public class JavaClassModelDescriptionProviderTest {

    JavaClassModelDescriptionProvider modelDescriptionProvider = new JavaClassModelDescriptionProvider();

    @Test
    public void getModelDescription() throws ModelDescriptionProviderException {
        String className = "ru.lokoproject.summer.common.data.model.provider.classes.FirstTestClass";

        modelDescriptionProvider.setRootProvider(modelDescriptionProvider);

        ModelDescription modelDescription = modelDescriptionProvider.getModelDescription(className, Map.of(ModelDescriptionProvider.MODEL_LEVEL, 1));
    }
}