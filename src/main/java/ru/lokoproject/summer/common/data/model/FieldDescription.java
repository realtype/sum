package ru.lokoproject.summer.common.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldDescription {
    String name;
    String type;
    String collectionType;
    Boolean isCollection;
    Boolean isPrimitive;
    ModelDescription modelDescription;
}
