package ukitinu.elastic_spring.filters;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static ukitinu.elastic_spring.filters.RestConstants.*;

@ControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<JSONObject>
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass)
    {
        LOG.debug("Return type is a {}", methodParameter.getParameterType());
        return methodParameter.getParameterType() == JSONObject.class;
    }

    @Override
    public JSONObject beforeBodyWrite(JSONObject response, MethodParameter methodParameter, MediaType mediaType,
                                      Class<? extends HttpMessageConverter<?>> aClass,
                                      ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse)
    {
        LOG.debug("Checking response body");
        if (response == null) throw new NullPointerException("Null body returned");
        addValues(response);
        return response;
    }

    @SuppressWarnings("unchecked")
    private void addValues(JSONObject response)
    {
        if (!response.containsKey(STATUS)) {
            LOG.debug(ADDING_FIELD, STATUS);
            response.put(STATUS, HttpStatus.OK.value());
        }
        if (!response.containsKey(MESSAGE)) {
            LOG.debug(ADDING_FIELD, MESSAGE);
            response.put(MESSAGE, HttpStatus.OK.getReasonPhrase());
        }
        if (!response.containsKey(TS)) {
            LOG.debug(ADDING_FIELD, TS);
            response.put(TS, System.currentTimeMillis());
        }
    }
}
