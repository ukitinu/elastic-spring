package ukitinu.elastic_spring.entities.users;

final class UserConstants
{
    private UserConstants()
    {
        throw new IllegalStateException("Constants class");
    }

    static final String USER_NAME = "name";
    static final String USER_EMAIL = "email";
    static final String USER_PASSWORD = "password";
    static final String USER_ROLE = "role";
    static final String USER_LAST_LOGIN_DATE = "last_update_date";
}
