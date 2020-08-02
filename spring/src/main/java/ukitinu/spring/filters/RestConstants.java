package ukitinu.spring.filters;

public final class RestConstants
{
    private RestConstants()
    {
        throw new IllegalStateException("Constants class");
    }

    static final String STATUS = "_status";
    static final String TS = "_ts";
    public static final String MESSAGE = "_message";

    public static final String RESPONSE = "response";
    public static final String OLD = "old";
    public static final String NEW = "new";

    static final String ADDING_FIELD = "Adding \"{}\" to response body";
}
