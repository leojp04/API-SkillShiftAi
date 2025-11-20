package br.com.skillshift.bo;

import br.com.skillshift.dao.UsuarioDAO;
import br.com.skillshift.dto.UsuarioDTO;
import br.com.skillshift.exception.BadRequestException;
import br.com.skillshift.exception.NotFoundException;
import br.com.skillshift.model.TipoPerfil;
import br.com.skillshift.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class UsuarioBO {

    @Inject
    UsuarioDAO usuarioDAO;

    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioDAO.buscarPorId(id).orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));
    }

    public Usuario criar(UsuarioDTO dto) {
        validar(dto, null);
        Usuario usuario = toEntity(dto, new Usuario());
        usuario.setCriadoEm(LocalDate.now());
        return usuarioDAO.criar(usuario);
    }

    public Usuario atualizar(Long id, UsuarioDTO dto) {
        Usuario existente = buscarPorId(id);
        validar(dto, existente);
        Usuario atualizado = toEntity(dto, existente);
        return usuarioDAO.atualizar(atualizado);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        usuarioDAO.deletar(id);
    }

    private void validar(UsuarioDTO dto, Usuario existente) {
        if (dto == null) {
            throw new BadRequestException("Dados do usuario sao obrigatorios");
        }
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new BadRequestException("Nome obrigatorio");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new BadRequestException("Email obrigatorio");
        }
        if (!dto.getEmail().contains("@")) {
            throw new BadRequestException("Email invalido");
        }
        usuarioDAO.buscarPorEmail(dto.getEmail()).ifPresent(u -> {
            if (existente == null || !u.getIdUsuario().equals(existente.getIdUsuario())) {
                throw new BadRequestException("Email ja cadastrado");
            }
        });

        if (dto.getIdade() == null || dto.getIdade() < 16 || dto.getIdade() > 80) {
            throw new BadRequestException("Idade deve estar entre 16 e 80 anos");
        }
        if (dto.getTipoPerfil() == null || !isPerfilValido(dto.getTipoPerfil())) {
            throw new BadRequestException("Tipo de perfil invalido");
        }
        if (dto.getEscolaridade() == null || dto.getEscolaridade().isBlank()) {
            throw new BadRequestException("Escolaridade obrigatoria");
        }
        if (dto.getAreaAtual() == null || dto.getAreaAtual().isBlank()) {
            throw new BadRequestException("Area atual obrigatoria");
        }
        if (dto.getSenha() == null || dto.getSenha().isBlank()) {
            throw new BadRequestException("Senha obrigatoria");
        }
    }

    private Usuario toEntity(UsuarioDTO dto, Usuario usuario) {
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenhaHash(dto.getSenha());
        usuario.setIdade(dto.getIdade());
        usuario.setEscolaridade(dto.getEscolaridade());
        usuario.setAreaAtual(dto.getAreaAtual());
        usuario.setNivelRisco(dto.getNivelRisco());
        usuario.setTipoPerfil(dto.getTipoPerfil());
        return usuario;
    }

    private boolean isPerfilValido(TipoPerfil perfil) {
        return perfil == TipoPerfil.USER || perfil == TipoPerfil.ADMIN || perfil == TipoPerfil.EMPRESA;
    }
}
