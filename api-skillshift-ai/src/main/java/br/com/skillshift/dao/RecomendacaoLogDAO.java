package br.com.skillshift.dao;

import br.com.skillshift.model.RecomendacaoLog;
import java.util.List;
import java.util.Optional;

public interface RecomendacaoLogDAO {
    List<RecomendacaoLog> listarTodos();
    Optional<RecomendacaoLog> buscarPorId(Long id);
    RecomendacaoLog criar(RecomendacaoLog log);
    RecomendacaoLog atualizar(RecomendacaoLog log);
    void deletar(Long id);
}
