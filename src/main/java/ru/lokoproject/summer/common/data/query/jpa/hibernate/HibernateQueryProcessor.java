package ru.lokoproject.summer.common.data.query.jpa.hibernate;

import org.hibernate.Session;
import ru.lokoproject.summer.common.data.query.*;
import ru.lokoproject.summer.common.data.util.FieldPathUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static ru.lokoproject.summer.common.data.query.QueryTypes.*;
import static ru.lokoproject.summer.common.data.util.ConversionUtil.getNumberValue;

@SuppressWarnings({"unchecked", "rawtypes"})
public class HibernateQueryProcessor implements QueryProcessor {


    void processQuery(Query query){
        try {
            Class entityClass = Class.forName(query.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Session session = null;
        CriteriaBuilder builder = session.getCriteriaBuilder();

    }

    Predicate[] createPredicates(List<Query> queryList, CriteriaBuilder builder, Root root){
        Predicate[] result = new Predicate[queryList.size()];
        int i=0;
        for(Query query: queryList){
            if (query.isGroup()){
                result[i]=createGroupPredicate(query, builder, root);
            }
            else  {
                result[i]=createSpecificPredicate(query, builder, root);
            }
            i++;
        }
        return result;
    }

    Predicate createGroupPredicate(Query query, CriteriaBuilder builder, Root root){
        GroupQuery groupQuery = (GroupQuery) query;
        if (OR.equalsIgnoreCase(query.getType())){
            builder.or(createPredicates(groupQuery.getChildQueries(), builder, root));
        }
        else if (AND.equalsIgnoreCase(query.getType())){
            builder.and(createPredicates(groupQuery.getChildQueries(), builder, root));
        }
        throw new QueryExecutionException(String.format("unknown group query type '%s'", query.getType()), query);
    }

    Predicate createSpecificPredicate(Query query, CriteriaBuilder builder, Root root){
        SpecificQuery specificQuery = (SpecificQuery) query;
        if(FieldPathUtil.isFieldPathFinal(specificQuery.getPath())){

            if((specificQuery.getParams() == null) || (specificQuery.getParams().size() == 0))
                throw new  QueryExecutionException("empty param for query", query);

            if(GT.equalsIgnoreCase(specificQuery.getType())){
                builder.gt(root.get(specificQuery.getPath()), getNumberValue(specificQuery.getParams().get(0)));
            }
            else if(GE.equalsIgnoreCase(specificQuery.getType())){
                builder.ge(root.get(specificQuery.getPath()), getNumberValue(specificQuery.getParams().get(0)));
            }
            else if(LT.equalsIgnoreCase(specificQuery.getType())){
                builder.lt(root.get(specificQuery.getPath()), getNumberValue(specificQuery.getParams().get(0)));
            }
            else if(LE.equalsIgnoreCase(specificQuery.getType())){
                builder.le(root.get(specificQuery.getPath()), getNumberValue(specificQuery.getParams().get(0)));
            }
            else if(EQ.equalsIgnoreCase(specificQuery.getType())){
                builder.equal(root.get(specificQuery.getPath()), getNumberValue(specificQuery.getParams().get(0)));  // TODO: 26.08.2021 преобразование к типу модели
            }
            else if(LIKE.equalsIgnoreCase(specificQuery.getType())){
                builder.like(root.get(specificQuery.getPath()), specificQuery.getParams().get(0));
            }
            // TODO: 27.08.2021
//            else if(BETWEEN.equalsIgnoreCase(specificQuery.getType())){
//                if(specificQuery.getParams().size() < 2)
//                    throw new  QueryExecutionException("empty second param for between query", query);
//
//                builder.between(root.get(specificQuery.getPath()),
//                        getNumberValue(specificQuery.getParams().get(0)),
//                        getNumberValue(specificQuery.getParams().get(1))
//                        );
//            }
        }

        throw new QueryExecutionException(String.format("unknown specific query type '%s'", query.getType()), query);
    }
}
