package ru.lokoproject.summer.common.data.model.provider;


import org.junit.Test;
import ru.lokoproject.summer.common.data.model.ModelDescription;

class JavaClassModelDescriptionProviderTest {

    JavaClassModelDescriptionProvider modelDescriptionProvider;

    @Test
    void getModelDescription() {
        String className = "ru.lokoproject.summer.common.data.model.provider.classes.FirstTestClass";

        ModelDescription modelDescription = modelDescriptionProvider.getModelDescription(className);
    }
}