package br.com.skillshift.config;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
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

        if (exception instanceof NotFoundException) {
            return json(Response.Status.NOT_FOUND, exception.getMessage(), null);
        }

        if (exception instanceof IllegalArgumentException) {
            return json(Response.Status.BAD_REQUEST, exception.getMessage(), null);
        }

        return json(Response.Status.INTERNAL_SERVER_ERROR, "Erro interno inesperado", null);
    }

    private Response json(Response.Status status, String message, List<String> details) {
        Map<String, Object> body = Map.of(
                "message", message,
                "details", details
        );
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}
