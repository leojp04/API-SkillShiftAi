package br.com.skillshift.dao.impl;

import br.com.skillshift.dao.RecomendacaoDAO;
import br.com.skillshift.model.Recomendacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RecomendacaoDAOImpl implements RecomendacaoDAO {

    @Inject
    EntityManager entityManager;

    @Override
    public List<Recomendacao> listarTodos() {
        return entityManager.createQuery("select r from Recomendacao r", Recomendacao.class).getResultList();
    }

    @Override
    public List<Recomendacao> buscarPorUsuario(Long usuarioId) {
        return entityManager.createQuery("select r from Recomendacao r where r.usuario.idUsuario = :usuarioId", Recomendacao.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }

    @Override
    public Optional<Recomendacao> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager.find(Recomendacao.class, id));
    }

    @Override
    @Transactional
    public Recomendacao criar(Recomendacao recomendacao) {
        entityManager.persist(recomendacao);
        return recomendacao;
    }

    @Override
    @Transactional
    public Recomendacao atualizar(Recomendacao recomendacao) {
        return entityManager.merge(recomendacao);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        buscarPorId(id).ifPresent(entityManager::remove);
    }
}
