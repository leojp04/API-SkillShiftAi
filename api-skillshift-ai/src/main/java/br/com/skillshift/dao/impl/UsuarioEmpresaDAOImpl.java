package br.com.skillshift.dao.impl;

import br.com.skillshift.dao.UsuarioEmpresaDAO;
import br.com.skillshift.model.UsuarioEmpresa;
import br.com.skillshift.model.UsuarioEmpresaId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioEmpresaDAOImpl implements UsuarioEmpresaDAO {

    @Inject
    EntityManager entityManager;

    @Override
    public List<UsuarioEmpresa> listarTodos() {
        return entityManager.createQuery("select ue from UsuarioEmpresa ue", UsuarioEmpresa.class).getResultList();
    }

    @Override
    public Optional<UsuarioEmpresa> buscarPorId(UsuarioEmpresaId id) {
        return Optional.ofNullable(entityManager.find(UsuarioEmpresa.class, id));
    }

    @Override
    @Transactional
    public UsuarioEmpresa criar(UsuarioEmpresa usuarioEmpresa) {
        entityManager.persist(usuarioEmpresa);
        return usuarioEmpresa;
    }

    @Override
    @Transactional
    public UsuarioEmpresa atualizar(UsuarioEmpresa usuarioEmpresa) {
        return entityManager.merge(usuarioEmpresa);
    }

    @Override
    @Transactional
    public void deletar(UsuarioEmpresaId id) {
        buscarPorId(id).ifPresent(entityManager::remove);
    }
}
