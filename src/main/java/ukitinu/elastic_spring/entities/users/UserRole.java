package ukitinu.elastic_spring.entities.users;

public enum UserRole
{
    ADMIN,
    USER;

    @Override
    public String toString()
    {
        return "ROLE_" + this.name().toUpperCase();
    }
}
