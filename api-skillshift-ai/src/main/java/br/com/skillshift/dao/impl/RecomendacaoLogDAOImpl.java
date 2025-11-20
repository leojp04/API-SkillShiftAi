package br.com.skillshift.dao.impl;

import br.com.skillshift.dao.RecomendacaoLogDAO;
import br.com.skillshift.model.RecomendacaoLog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RecomendacaoLogDAOImpl implements RecomendacaoLogDAO {

    @Inject
    EntityManager entityManager;

    @Override
    public List<RecomendacaoLog> listarTodos() {
        return entityManager.createQuery("select r from RecomendacaoLog r", RecomendacaoLog.class).getResultList();
    }

    @Override
    public Optional<RecomendacaoLog> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager.find(RecomendacaoLog.class, id));
    }

    @Override
    @Transactional
    public RecomendacaoLog criar(RecomendacaoLog log) {
        entityManager.persist(log);
        return log;
    }

    @Override
    @Transactional
    public RecomendacaoLog atualizar(RecomendacaoLog log) {
        return entityManager.merge(log);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        buscarPorId(id).ifPresent(entityManager::remove);
    }
}
