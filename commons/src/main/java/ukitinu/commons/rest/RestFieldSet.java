package ukitinu.commons.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RestFieldSet
{
    private static final RestFieldSet EMPTY_SET = new RestFieldSet();

    private final Set<RestField<?>> restFields = new HashSet<>();

    public static RestFieldSet emptySet()
    {
        return new RestFieldSet().addAll(EMPTY_SET);
    }

    Set<RestField<?>> getSet()
    {
        return new HashSet<>(restFields);
    }

    public RestFieldSet addString(String fieldName)
    {
        restFields.add(new RestField<>(fieldName, String.class));
        return this;
    }

    public RestFieldSet addInt(String fieldName)
    {
        restFields.add(new RestField<>(fieldName, Integer.class));
        return this;
    }

    public RestFieldSet addBool(String fieldName)
    {
        restFields.add(new RestField<>(fieldName, Boolean.class));
        return this;
    }

    public RestFieldSet addLong(String fieldName)
    {
        restFields.add(new RestField<>(fieldName, Long.class));
        return this;
    }

    public RestFieldSet addDouble(String fieldName)
    {
        restFields.add(new RestField<>(fieldName, Double.class));
        return this;
    }

    public RestFieldSet addList(String fieldName)
    {
        restFields.add(new RestField<>(fieldName, List.class));
        return this;
    }

    public RestFieldSet addMap(String fieldName)
    {
        restFields.add(new RestField<>(fieldName, Map.class));
        return this;
    }

    public RestFieldSet addAll(RestFieldSet... otherSets)
    {
        if (otherSets != null && otherSets.length > 0) {
            for (RestFieldSet set : otherSets) {
                if (set != null) restFields.addAll(set.getSet());
            }
        }
        return this;
    }
}
