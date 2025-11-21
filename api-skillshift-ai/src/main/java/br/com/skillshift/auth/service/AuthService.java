package br.com.skillshift.auth.service;

import br.com.skillshift.auth.dto.LoginRequest;
import br.com.skillshift.auth.dto.LoginResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AuthService {

    @ConfigProperty(name = "admin.username", defaultValue = "admin")
    String adminUsername;

    @ConfigProperty(name = "admin.password", defaultValue = "admin123")
    String adminPassword;

    @ConfigProperty(name = "admin.token.ttl.seconds", defaultValue = "3600")
    long tokenTtlSeconds;

    public LoginResponse login(LoginRequest request) {
        if (!adminUsername.equals(request.getUsername()) || !adminPassword.equals(request.getPassword())) {
            throw new WebApplicationException("Credenciais inválidas", Response.Status.UNAUTHORIZED);
        }

        LoginResponse response = new LoginResponse();
        // UUID simples em vez de JWT só para liberar painel admin rapidamente.
        response.setAccessToken(UUID.randomUUID().toString());
        response.setExpiresIn(tokenTtlSeconds);
        response.setMessage("Login realizado com sucesso");
        return response;
    }
}
