package ukitinu.commons.exceptions;

import org.springframework.http.HttpStatus;

public interface WithHttpStatus
{
    HttpStatus getHttpStatus();
}
