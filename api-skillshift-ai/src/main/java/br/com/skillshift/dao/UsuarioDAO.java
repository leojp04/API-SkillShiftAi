package br.com.skillshift.dao;

import br.com.skillshift.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioDAO {
    List<Usuario> listarTodos();
    Optional<Usuario> buscarPorId(Long id);
    Usuario criar(Usuario usuario);
    Usuario atualizar(Usuario usuario);
    void deletar(Long id);
    Optional<Usuario> buscarPorEmail(String email);
}
