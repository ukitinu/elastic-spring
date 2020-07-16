package ukitinu.elastic_spring.exceptions;

import org.springframework.http.HttpStatus;

public interface WithHttpStatus
{
    HttpStatus getHttpStatus();
}
