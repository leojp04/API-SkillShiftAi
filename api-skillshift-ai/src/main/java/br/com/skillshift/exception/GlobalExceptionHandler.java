package br.com.skillshift.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("timestamp", LocalDateTime.now().toString());
        payload.put("error", exception.getClass().getSimpleName());
        payload.put("message", exception.getMessage());

        if (exception instanceof BadRequestException) {
            return build(Response.Status.BAD_REQUEST, payload);
        }
        if (exception instanceof NotFoundException) {
            return build(Response.Status.NOT_FOUND, payload);
        }
        if (exception instanceof IAServiceIndisponivelException) {
            return build(Response.Status.SERVICE_UNAVAILABLE, payload);
        }
        return build(Response.Status.INTERNAL_SERVER_ERROR, payload);
    }

    private Response build(Response.Status status, Map<String, Object> payload) {
        return Response.status(status)
                .entity(payload)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
