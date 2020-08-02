package ukitinu.spring.entities.posts;

final class PostConstants
{
    private PostConstants()
    {
        throw new IllegalStateException("Constants class");
    }

    static final String POST_TITLE = "title";
    static final String POST_CONTENT = "content";
    static final String POST_AUTH_NAME = "author_name";
    static final String POST_AUTH_ID = "author_id";
}
