package br.com.skillshift.dao;

import br.com.skillshift.model.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoDAO {
    List<Curso> listarTodos();
    Optional<Curso> buscarPorId(Long id);
    Optional<Curso> buscarPorNome(String nome);
    Curso criar(Curso curso);
    Curso atualizar(Curso curso);
    void deletar(Long id);
}
