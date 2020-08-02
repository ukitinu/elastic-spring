package ukitinu.spring.entities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import ukitinu.database.DatabaseManager;
import ukitinu.database.SearchResult;
import ukitinu.database.exceptions.DatabaseException;
import ukitinu.database.search.SearchEntity;
import ukitinu.commons.exceptions.RequestException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class EntityService<E extends AbstractEntity>
{
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    private static final DatabaseManager DB = DatabaseManager.INSTANCE;

    private final Class<? extends E> entityClass;
    private final String index;

    public EntityService(String index, Class<? extends E> entityClass)
    {
        this.entityClass = entityClass;
        this.index = index;
    }

    public E add(Map<String, ?> newObject) throws RequestException, EntityException, DatabaseException
    {
        E newEntity = mapNewEntity(newObject);
        String id = newEntity.createId();
        newEntity.setId(id);
        newEntity.setInsertionDate(System.currentTimeMillis());

        if (exists(id)) throw new RequestException("Cannot overwrite existing entry", HttpStatus.CONFLICT);
        DB.indexSingle(index, id, newEntity.toJson());
        return newEntity;
    }

    public E update(String id, Map<String, ?> updates) throws DatabaseException, EntityException, RequestException
    {
        E current = DB.get(index, id).toEntity(entityClass);
        current.updateWith(mapNewEntity(updates));
        DB.updateSingle(index, id, current.toJson());
        return current;
    }

    public E get(String id) throws DatabaseException
    {
        return DB.get(index, id).toEntity(entityClass);
    }

    public boolean exists(String id) throws DatabaseException
    {
        return DB.exists(index, id);
    }

    public E delete(String id) throws DatabaseException
    {
        return exists(id) ? DB.pop(index, id).toEntity(entityClass) : null;
    }

    public SearchResult search(SearchEntity searchEntity) throws DatabaseException
    {
        return DB.search(searchEntity.toSearch(index));
    }

    public long count(SearchEntity searchEntity) throws DatabaseException
    {
        return DB.count(index, searchEntity.buildQuery());
    }

    private E mapNewEntity(Map<String, ?> value) throws RequestException
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            E newEntity = objectMapper.readValue(value.toString(), entityClass);
            validateEntity(newEntity);
            return newEntity;
        } catch (IOException e) {
            throw new RequestException("Unable to read input value", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateEntity(Object entity) throws RequestException
    {
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<Object> violation : violations) {
                stringBuilder.append("\n").append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("\n");
            }
            throw new RequestException("Invalid input: " + stringBuilder.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
