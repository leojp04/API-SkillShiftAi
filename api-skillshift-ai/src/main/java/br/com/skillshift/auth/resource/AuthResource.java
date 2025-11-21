package br.com.skillshift.auth.resource;

import br.com.skillshift.auth.dto.LoginRequest;
import br.com.skillshift.auth.dto.LoginResponse;
import br.com.skillshift.auth.service.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/admin/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    public LoginResponse login(@Valid LoginRequest request) {
        return authService.login(request);
    }
}
