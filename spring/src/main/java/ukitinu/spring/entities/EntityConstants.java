package ukitinu.spring.entities;

final class EntityConstants
{
    private EntityConstants()
    {
        throw new IllegalStateException("Constants class");
    }

    static final String ENT_ID = "id";
    static final String ENT_INSERTION_DATE = "insertion_date";
    static final String ENT_DELETION_DATE = "deletion_date";
    static final String ENT_LAST_UPDATE_DATE = "last_update_date";
}
