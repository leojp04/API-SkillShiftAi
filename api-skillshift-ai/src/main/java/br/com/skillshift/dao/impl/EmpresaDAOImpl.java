package br.com.skillshift.dao.impl;

import br.com.skillshift.dao.EmpresaDAO;
import br.com.skillshift.model.Empresa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EmpresaDAOImpl implements EmpresaDAO {

    @Inject
    EntityManager entityManager;

    @Override
    public List<Empresa> listarTodos() {
        return entityManager.createQuery("select e from Empresa e", Empresa.class).getResultList();
    }

    @Override
    public Optional<Empresa> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager.find(Empresa.class, id));
    }

    @Override
    @Transactional
    public Empresa criar(Empresa empresa) {
        entityManager.persist(empresa);
        return empresa;
    }

    @Override
    @Transactional
    public Empresa atualizar(Empresa empresa) {
        return entityManager.merge(empresa);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        buscarPorId(id).ifPresent(entityManager::remove);
    }
}
