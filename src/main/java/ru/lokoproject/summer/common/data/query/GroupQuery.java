package ru.lokoproject.summer.common.data.query;

import lombok.Getter;

import java.util.List;

@Getter
public class GroupQuery extends Query {
    public enum Type {
        OR, AND
    }

    Type type;
    List<Query> childQueries;
}
