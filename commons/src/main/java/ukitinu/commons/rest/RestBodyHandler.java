package ukitinu.commons.rest;

import org.springframework.http.HttpStatus;
import ukitinu.commons.exceptions.RequestException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public final class RestBodyHandler
{
    private RestBodyHandler()
    {
        throw new IllegalStateException("Utility class");
    }

    private static final String EXTRACT_ERROR = "Field %s must be a %s";

    /**
     * Checks that all required fields are present and of correct type and that all optional fields are of correct type.
     * if this is not the case throws a {@link RequestException} with message
     * detailing the problems.
     *
     * @param input    request body.
     * @param required required fields.
     * @param optional optional fields.
     */
    public static void checkInput(Map<String, Object> input, RestFieldSet required, RestFieldSet optional) throws RequestException
    {
        Collection<String> missing = new HashSet<>();
        Collection<String> badType = new HashSet<>();
        checkRequired(input, required, missing, badType);
        checkOptional(input, optional, badType);
        sendErrorResponse(missing, badType);
    }

    /**
     * Removes from the body all fields not listed in at least one of the {@param retainedSets}.
     *
     * @param input        request body.
     * @param retainedSets field to maintain (if empty, response is empty too).
     */
    public static void retain(Map<String, Object> input, RestFieldSet... retainedSets)
    {
        if (retainedSets == null || retainedSets.length == 0) {
            input.clear();
        } else {
            RestFieldSet retained = new RestFieldSet().addAll(retainedSets);
            Collection<String> toRetain = retained
                    .getSet()
                    .stream()
                    .map(RestField::getName)
                    .collect(Collectors.toSet());

            Collection<String> toRemove = new ArrayList<>();
            for (String key : input.keySet()) {
                if (!toRetain.contains(key)) toRemove.add(key);
            }
            for (String key : toRemove) input.remove(key);
        }
    }

    private static void checkRequired(Map<String, Object> input, RestFieldSet required,
                                      Collection<? super String> missing, Collection<? super String> badType)
    {
        for (RestField<?> field : required.getSet()) {
            String name = field.getName();
            if (!input.containsKey(name)) {
                missing.add(field.toString());
            } else if (field.isWrongType(input.get(name))) {
                badType.add(field.toString());
            }
        }
    }

    private static void checkOptional(Map<String, Object> input, RestFieldSet optional, Collection<? super String> badType)
    {
        for (RestField<?> field : optional.getSet()) {
            String name = field.getName();
            if (input.containsKey(name)) {
                if (field.isWrongType(input.get(name))) {
                    badType.add(field.toString(true));
                }
            }
        }
    }

    private static void sendErrorResponse(Collection<String> missing, Collection<String> badType) throws RequestException
    {
        String missingFields = missing.isEmpty() ? "" : String.join(", ", missing);
        String badTypeFields = badType.isEmpty() ? "" : String.join(", ", badType);
        if (!missingFields.isEmpty() || !badTypeFields.isEmpty()) {
            String absent = missingFields.isEmpty() ? "" : "Field(s) missing: " + missingFields;
            String wrong = badTypeFields.isEmpty() ? "" : "Wrong type(s): " + badTypeFields;
            String response = absent.isEmpty() ?
                    wrong : wrong.isEmpty() ? absent : absent + System.lineSeparator() + wrong;
            throw new RequestException(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Extracts the value of {@code body.field}, casting it to the appropriate type.
     */
    public static <T> T extract(Map<String, Object> body, String field, Class<T> tClass)
    {
        try {
            return tClass.cast(body.get(field));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(String.format(EXTRACT_ERROR, field, tClass.getSimpleName()));
        }
    }

    /**
     * Extracts the value of {@code body.field}, casting it to {@link Long}.
     * Handles the eventuality that the value is not recognised as {@code long} when reading the body.
     */
    public static Long extractLong(Map<String, Object> body, String field)
    {
        try {
            Number number = (Number) body.get(field);
            return number.longValue();
        } catch (ClassCastException e) {
            String message = "Field \"" + field + "\" must be a number";
            throw new IllegalArgumentException(String.format(EXTRACT_ERROR, field, "number"));
        }
    }
}
