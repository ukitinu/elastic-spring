package ukitinu.elastic_spring.entities.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import ukitinu.elastic_spring.entities.AbstractEntity;
import ukitinu.elastic_spring.utils.CommonMethods;

import java.util.Objects;

import static ukitinu.elastic_spring.entities.users.UserConstants.*;


public class User extends AbstractEntity
{
    @JsonProperty(value = USER_NAME)
    private String name;
    @JsonProperty(value = USER_EMAIL)
    private String email;
    @JsonProperty(value = USER_PASSWORD)
    private String password;
    @JsonProperty(value = USER_ROLE, defaultValue = "USER")
    private UserRole role;
    @JsonProperty(value = USER_LAST_LOGIN_DATE)
    private long lastLoginDate;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public UserRole getRole()
    {
        return role;
    }

    public void setRole(UserRole role)
    {
        this.role = role;
    }

    public long getLastLoginDate()
    {
        return lastLoginDate;
    }

    public void setLastLoginDate(long lastLoginDate)
    {
        this.lastLoginDate = lastLoginDate;
    }

    @Override
    public void updateWith(AbstractEntity updates)
    {
        if (updates instanceof User) {
            User update = (User) updates;
            if (update.email != null) this.email = update.email;
            if (update.password != null) this.password = update.password;
            this.setLastUpdateDate(System.currentTimeMillis());
        } else {
            throw new IllegalArgumentException("\"updates\" is not an User");
        }
    }

    @Override
    public String createId()
    {
        return CommonMethods.getMd5(name);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(createId());
    }

    @Override
    public String toString()
    {
        return String.format("[%5s] %s:%s", role.name(), name, email);
    }
}
