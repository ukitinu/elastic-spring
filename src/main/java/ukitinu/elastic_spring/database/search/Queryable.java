package ukitinu.elastic_spring.database.search;

import org.elasticsearch.index.query.QueryBuilder;

public interface Queryable
{
    QueryBuilder toQuery();
}
