package ukitinu.elastic_spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static ukitinu.elastic_spring.entities.EntityConstants.*;

public abstract class AbstractEntity
{
    private final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final JSONParser jsonParser = new JSONParser();

    @JsonProperty(value = ENT_ID)
    private String id;
    @JsonProperty(value = ENT_INSERTION_DATE)
    private long insertionDate;
    @JsonProperty(value = ENT_DELETION_DATE)
    private long deletionDate;
    @JsonProperty(value = ENT_LAST_UPDATE_DATE)
    private long lastUpdateDate;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public long getInsertionDate()
    {
        return insertionDate;
    }

    public void setInsertionDate(long insertionDate)
    {
        this.insertionDate = insertionDate;
    }

    public long getDeletionDate()
    {
        return deletionDate;
    }

    public void setDeletionDate(long deletionDate)
    {
        this.deletionDate = deletionDate;
    }

    public long getLastUpdateDate()
    {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDate)
    {
        this.lastUpdateDate = lastUpdateDate;
    }

    public abstract String createId();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    public abstract void updateWith(AbstractEntity updates);

    @JsonIgnore
    public JSONObject toJson() throws EntityException
    {
        try {
            return (JSONObject) jsonParser.parse(objectMapper.writeValueAsString(this));
        } catch (Exception e) {
            throw new EntityException("Serialisation error", e);
        }
    }

    @JsonIgnore
    public JSONObject toResponseJson() throws EntityException
    {
        return toJson();
    }
}
