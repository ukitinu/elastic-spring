package ukitinu.elastic_spring.entities.posts;

import com.fasterxml.jackson.annotation.JsonProperty;
import ukitinu.elastic_spring.entities.AbstractEntity;
import ukitinu.elastic_spring.utils.CommonMethods;

import java.util.Objects;

import static ukitinu.elastic_spring.entities.posts.PostConstants.*;


public class Post extends AbstractEntity
{
    @JsonProperty(value = POST_TITLE)
    private String title;
    @JsonProperty(value = POST_CONTENT)
    private String content;
    @JsonProperty(value = POST_AUTH_ID)
    private String authorId;
    @JsonProperty(value = POST_AUTH_NAME)
    private String authorName;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(String authorId)
    {
        this.authorId = authorId;
    }

    public String getAuthorName()
    {
        return authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    @Override
    public void updateWith(AbstractEntity updates)
    {
        if(updates instanceof Post) {
            Post update = (Post) updates;
            if (update.title != null) this.title = update.title;
            if (update.content != null) this.content = update.content;
            this.setLastUpdateDate(System.currentTimeMillis());
        } else {
            throw new IllegalArgumentException("\"updates\" is not a Post");
        }
    }

    @Override
    public String createId()
    {
        return CommonMethods.getMd5(authorId + title);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return title.equals(post.title) &&
                authorId.equals(post.authorId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(createId());
    }

    @Override
    public String toString()
    {
        return String.format("%s: %s", authorName, title);
    }
}
