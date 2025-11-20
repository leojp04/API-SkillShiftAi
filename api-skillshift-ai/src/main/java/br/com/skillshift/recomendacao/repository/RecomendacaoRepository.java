package br.com.skillshift.recomendacao.repository;

import br.com.skillshift.recomendacao.model.RecomendacaoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RecomendacaoRepository implements PanacheRepositoryBase<RecomendacaoEntity, UUID> {

    public List<RecomendacaoEntity> listarFiltrado(String area, String senioridade) {
        if (area == null && senioridade == null) {
            return listAll();
        }
        if (area != null && senioridade != null) {
            return list("lower(area) = ?1 and lower(senioridade) = ?2", area.toLowerCase(), senioridade.toLowerCase());
        }
        if (area != null) {
            return list("lower(area) = ?1", area.toLowerCase());
        }
        return list("lower(senioridade) = ?1", senioridade.toLowerCase());
    }
}
