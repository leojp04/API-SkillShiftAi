package br.com.skillshift.dao;

import br.com.skillshift.model.Empresa;
import java.util.List;
import java.util.Optional;

public interface EmpresaDAO {
    List<Empresa> listarTodos();
    Optional<Empresa> buscarPorId(Long id);
    Empresa criar(Empresa empresa);
    Empresa atualizar(Empresa empresa);
    void deletar(Long id);
}
