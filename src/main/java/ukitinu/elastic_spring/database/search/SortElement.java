package ukitinu.elastic_spring.database.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Objects;

class SortElement
{
    private static final String JSON_FIELD = "field";
    private static final String JSON_IS_ASCENDING = "is_ascending";

    private final String field;
    private final boolean isAscending;

    @JsonCreator
    SortElement(@JsonProperty(JSON_FIELD) String field, @JsonProperty(JSON_IS_ASCENDING) boolean isAscending)
    {
        this.field = field;
        this.isAscending = isAscending;
    }

    String getField()
    {
        return field;
    }

    SortOrder getSortOrder()
    {
        return isAscending ? SortOrder.ASC : SortOrder.DESC;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortElement that = (SortElement) o;
        return isAscending == that.isAscending &&
                Objects.equals(field, that.field);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(field, isAscending);
    }
}
