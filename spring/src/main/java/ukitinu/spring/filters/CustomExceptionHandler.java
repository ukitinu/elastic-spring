package ukitinu.spring.filters;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ukitinu.commons.exceptions.WithHttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

import static ukitinu.spring.filters.RestConstants.*;

@ControllerAdvice
public class CustomExceptionHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public JSONObject handleIllegalArgumentException(HttpServletRequest request, Exception ex)
    {
        return buildExceptionJson(request, ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(FileNotFoundException.class)
    public JSONObject handleFileNotFoundException(HttpServletRequest request, FileNotFoundException ex)
    {
        return buildExceptionJson(request, ex, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JSONObject handleException(HttpServletRequest request, Exception ex)
    {
        if(ex instanceof WithHttpStatus) {
            return buildExceptionJson(request, ex, ((WithHttpStatus) ex).getHttpStatus());
        }
        return buildExceptionJson(request, ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public JSONObject handleThrowable(HttpServletRequest request, Throwable throwable)
    {
        return buildExceptionJson(request, throwable, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SuppressWarnings("unchecked")
    private JSONObject buildExceptionJson(HttpServletRequest request, Throwable ex, HttpStatus status)
    {
        LOG.error("{} {} >> {} {}", request.getMethod(), request.getRequestURI(), ex.getClass().getSimpleName(), ex.getMessage());

        JSONObject jo = new JSONObject();
        jo.put(MESSAGE, ex.getMessage());
        jo.put(STATUS, status.value());
        jo.put(TS, System.currentTimeMillis());
        return jo;
    }
}
