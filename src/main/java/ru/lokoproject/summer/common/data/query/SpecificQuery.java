package ru.lokoproject.summer.common.data.query;

import java.util.List;

public class SpecificQuery extends Query {
    public enum Type{
        EQ, GT, GE, LT, LE, LIKE
    }

    List<String> params;
}
