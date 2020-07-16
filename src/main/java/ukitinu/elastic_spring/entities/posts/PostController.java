package ukitinu.elastic_spring.entities.posts;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ukitinu.elastic_spring.entities.AbstractEntityController;

@RestController
@RequestMapping(value = "posts/")
public class PostController extends AbstractEntityController<Post>
{
    public PostController()
    {
        super("posts");
    }
}
