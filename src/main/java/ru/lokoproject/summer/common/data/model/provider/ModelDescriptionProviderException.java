package ru.lokoproject.summer.common.data.model.provider;

public class ModelDescriptionProviderException extends Exception{
    public ModelDescriptionProviderException() {
    }

    public ModelDescriptionProviderException(String message) {
        super(message);
    }

    public ModelDescriptionProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelDescriptionProviderException(Throwable cause) {
        super(cause);
    }
}
