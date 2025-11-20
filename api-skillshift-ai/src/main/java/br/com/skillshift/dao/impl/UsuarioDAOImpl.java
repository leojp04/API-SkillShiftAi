package br.com.skillshift.dao.impl;

import br.com.skillshift.dao.UsuarioDAO;
import br.com.skillshift.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioDAOImpl implements UsuarioDAO {

    @Inject
    EntityManager entityManager;

    @Override
    public List<Usuario> listarTodos() {
        return entityManager.createQuery("select u from Usuario u", Usuario.class).getResultList();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(entityManager.find(Usuario.class, id));
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        List<Usuario> result = entityManager.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
                .setParameter("email", email)
                .getResultList();
        return result.stream().findFirst();
    }

    @Override
    @Transactional
    public Usuario criar(Usuario usuario) {
        entityManager.persist(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public Usuario atualizar(Usuario usuario) {
        return entityManager.merge(usuario);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        buscarPorId(id).ifPresent(entityManager::remove);
    }
}
