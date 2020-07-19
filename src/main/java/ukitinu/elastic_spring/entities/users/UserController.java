package ukitinu.elastic_spring.entities.users;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ukitinu.elastic_spring.database.exceptions.DatabaseException;
import ukitinu.elastic_spring.entities.AbstractEntityController;
import ukitinu.elastic_spring.entities.EntityException;
import ukitinu.elastic_spring.exceptions.RequestException;
import ukitinu.elastic_spring.utils.request_checker.RestBodyHandler;
import ukitinu.elastic_spring.utils.request_checker.RestFieldSet;

import java.util.Map;

import static ukitinu.elastic_spring.entities.users.UserConstants.*;

@RestController
@RequestMapping(value = "users/")
public class UserController extends AbstractEntityController<User> {
    private static final RestFieldSet USER_FIELDS = new RestFieldSet()
            .addString(USER_NAME).addString(USER_EMAIL).addString(USER_ROLE);

    public UserController() {
        super(DB_INDEX_USERS);
    }

    @Override
    public JSONObject add(Map<String, Object> body) throws EntityException, DatabaseException, RequestException {
        RestFieldSet userRequired = new RestFieldSet().addAll(USER_FIELDS).addString(USER_PASSWORD);
        RestBodyHandler.checkInput(body, userRequired, RestFieldSet.emptySet());
        return super.add(body);
    }

    @Override
    public JSONObject update(Map<String, Object> body, String id) throws EntityException, DatabaseException, RequestException {
        RestBodyHandler.checkInput(body, RestFieldSet.emptySet(), USER_FIELDS);
        RestBodyHandler.retain(body, USER_FIELDS);
        return super.update(body, id);
    }
}
