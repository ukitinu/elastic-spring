package ukitinu.elastic_spring.exceptions;

import org.springframework.http.HttpStatus;

public class RequestException extends Exception implements WithHttpStatus
{
    private static final long serialVersionUID = -4354118011136939995L;
    private final HttpStatus status;

    public RequestException(String message, HttpStatus status)
    {
        super(message);
        this.status = status;
    }

    public RequestException(String message, Throwable cause, HttpStatus status)
    {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getHttpStatus()
    {
        return status;
    }
}
