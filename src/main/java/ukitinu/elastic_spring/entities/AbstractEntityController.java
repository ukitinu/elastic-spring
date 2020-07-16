package ukitinu.elastic_spring.entities;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;
import ukitinu.elastic_spring.database.SearchResult;
import ukitinu.elastic_spring.database.exceptions.DatabaseException;
import ukitinu.elastic_spring.database.search.SearchEntity;
import ukitinu.elastic_spring.exceptions.RequestException;
import ukitinu.elastic_spring.filters.RestConstants;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@RestController
@SuppressWarnings("unchecked")
public abstract class AbstractEntityController<E extends AbstractEntity>
{
    private final EntityService<E> entityService;

    public AbstractEntityController(String index)
    {
        Class<E> entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityService = new EntityService<>(index, entityClass);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JSONObject add(@RequestBody Map<String, ?> body) throws EntityException, DatabaseException, RequestException
    {
        JSONObject response = new JSONObject();
        E added = entityService.add(body);
        response.put(RestConstants.RESPONSE, added.toResponseJson());
        response.put(RestConstants.MESSAGE, "Added");
        return response;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public JSONObject update(@RequestBody Map<String, ?> body, @PathVariable("id") String id) throws EntityException, DatabaseException, RequestException
    {
        JSONObject response = new JSONObject();

        E old = entityService.get(id);
        E updated = entityService.update(id, body);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(RestConstants.OLD, old.toResponseJson());
        responseMap.put(RestConstants.NEW, updated.toResponseJson());

        response.put(RestConstants.RESPONSE, responseMap);
        response.put(RestConstants.MESSAGE, id + " updated");
        return response;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public JSONObject get(@PathVariable("id") String id) throws DatabaseException, EntityException
    {
        JSONObject response = new JSONObject();
        E entity = entityService.get(id);
        response.put(RestConstants.RESPONSE, entity.toResponseJson());
        response.put(RestConstants.MESSAGE, id + " found");
        return response;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public JSONObject delete(@PathVariable("id") String id) throws DatabaseException, EntityException
    {
        JSONObject response = new JSONObject();
        E entity = entityService.delete(id);
        if (entity != null) {
            response.put(RestConstants.RESPONSE, entity.toResponseJson());
        } else {
            response.put(RestConstants.RESPONSE, new JSONObject());
        }
        response.put(RestConstants.MESSAGE, id + " deleted");
        return response;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public JSONObject search(@RequestBody SearchEntity searchEntity) throws DatabaseException
    {
        JSONObject response = new JSONObject();
        SearchResult result = entityService.search(searchEntity);
        response.put(RestConstants.RESPONSE, result.asMap());
        response.put(RestConstants.MESSAGE, "Search succeeded");
        return response;
    }

}
