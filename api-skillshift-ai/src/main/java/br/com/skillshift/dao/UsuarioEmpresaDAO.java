package br.com.skillshift.dao;

import br.com.skillshift.model.UsuarioEmpresa;
import br.com.skillshift.model.UsuarioEmpresaId;
import java.util.List;
import java.util.Optional;

public interface UsuarioEmpresaDAO {
    List<UsuarioEmpresa> listarTodos();
    Optional<UsuarioEmpresa> buscarPorId(UsuarioEmpresaId id);
    UsuarioEmpresa criar(UsuarioEmpresa usuarioEmpresa);
    UsuarioEmpresa atualizar(UsuarioEmpresa usuarioEmpresa);
    void deletar(UsuarioEmpresaId id);
}
