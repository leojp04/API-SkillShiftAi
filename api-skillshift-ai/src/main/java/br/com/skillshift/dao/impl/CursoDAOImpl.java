package br.com.skillshift.dao.impl;

import br.com.skillshift.dao.CursoDAO;
import br.com.skillshift.model.Curso;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CursoDAOImpl implements CursoDAO {

    @Inject
    EntityManager entityManager;

    @Override
    public List<Curso> listarTodos() {
        return entityManager.createQuery("select c from Curso c", Curso.class).getResultList();
    }

    @Override
    public Optional<Curso> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager.find(Curso.class, id));
    }

    @Override
    public Optional<Curso> buscarPorNome(String nome) {
        List<Curso> result = entityManager.createQuery("select c from Curso c where lower(c.nome) = :nome", Curso.class)
                .setParameter("nome", nome.toLowerCase())
                .getResultList();
        return result.stream().findFirst();
    }

    @Override
    @Transactional
    public Curso criar(Curso curso) {
        entityManager.persist(curso);
        return curso;
    }

    @Override
    @Transactional
    public Curso atualizar(Curso curso) {
        return entityManager.merge(curso);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        buscarPorId(id).ifPresent(entityManager::remove);
    }
}
