package br.com.skillshift.resource;

import br.com.skillshift.bo.AuthBO;
import br.com.skillshift.dto.LoginRequest;
import br.com.skillshift.dto.LoginResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthBO authBO;

    @POST
    @Path("/login")
    public LoginResponse login(LoginRequest request) {
        return authBO.login(request);
    }
}
