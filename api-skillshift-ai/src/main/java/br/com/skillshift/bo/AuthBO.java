package br.com.skillshift.bo;

import br.com.skillshift.dao.UsuarioDAO;
import br.com.skillshift.dto.LoginRequest;
import br.com.skillshift.dto.LoginResponse;
import br.com.skillshift.exception.BadRequestException;
import br.com.skillshift.exception.NotFoundException;
import br.com.skillshift.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Objects;

@ApplicationScoped
public class AuthBO {

    @Inject
    UsuarioDAO usuarioDAO;

    public LoginResponse login(LoginRequest request) {
        validarRequest(request);
        Usuario usuario = usuarioDAO.buscarPorEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado para o email informado"));

        if (!Objects.equals(usuario.getSenhaHash(), request.getSenha())) {
            throw new BadRequestException("Senha invalida");
        }

        String token = "TOKEN-" + usuario.getIdUsuario();
        return new LoginResponse(token, usuario);
    }

    private void validarRequest(LoginRequest request) {
        if (request == null || request.getEmail() == null || request.getEmail().isBlank()) {
            throw new BadRequestException("Email obrigatorio");
        }
        if (request.getSenha() == null || request.getSenha().isBlank()) {
            throw new BadRequestException("Senha obrigatoria");
        }
        if (!request.getEmail().contains("@")) {
            throw new BadRequestException("Email invalido");
        }
    }
}
