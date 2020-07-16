package ukitinu.elastic_spring.entities.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ukitinu.elastic_spring.entities.AbstractEntityController;

@RestController
@RequestMapping(value = "users/")
public class UserController extends AbstractEntityController<User>
{
    public UserController()
    {
        super("users");
    }
}
