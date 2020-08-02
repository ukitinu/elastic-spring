package ukitinu.spring.entities;

public class EntityException extends Exception
{
    private static final long serialVersionUID = -7658151413045698276L;

    public EntityException(String message)
    {
        super(message);
    }

    public EntityException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
