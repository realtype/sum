package ru.lokoproject.summer.common.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModelDescription {
    String className;
    List<FieldDescription> fieldDescriptions;
    List<String> interfaces;
}
