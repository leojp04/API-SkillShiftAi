package br.com.skillshift.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof ConstraintViolationException cve) {
            List<String> detalhes = cve.getConstraintViolations().stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.toList());
            return json(Response.Status.BAD_REQUEST, "Dados inválidos", detalhes);
        }

        if (exception instanceof ValidationException) {
            return json(Response.Status.BAD_REQUEST, "Dados inválidos", null);
        }

        if (exception instanceof JsonProcessingException) {
            return json(Response.Status.BAD_REQUEST, "JSON inválido", List.of(exception.getOriginalMessage()));
        }

        if (exception instanceof WebApplicationException wae) {
            return json(Response.Status.fromStatusCode(wae.getResponse().getStatus()), wae.getMessage(), null);
        }

        if (exception instanceof NotFoundException) {
            return json(Response.Status.NOT_FOUND, exception.getMessage(), null);
        }

        if (exception instanceof IllegalArgumentException) {
            return json(Response.Status.BAD_REQUEST, exception.getMessage(), null);
        }

        return json(Response.Status.INTERNAL_SERVER_ERROR, "Erro interno inesperado", null);
    }

    private Response json(Response.Status status, String message, List<String> details) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        if (details != null) {
            body.put("details", details);
        }
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
