package br.com.skillshift.dao;

import br.com.skillshift.model.Recomendacao;
import java.util.List;
import java.util.Optional;

public interface RecomendacaoDAO {
    List<Recomendacao> listarTodos();
    List<Recomendacao> buscarPorUsuario(Long usuarioId);
    Optional<Recomendacao> buscarPorId(Long id);
    Recomendacao criar(Recomendacao recomendacao);
    Recomendacao atualizar(Recomendacao recomendacao);
    void deletar(Long id);
}
