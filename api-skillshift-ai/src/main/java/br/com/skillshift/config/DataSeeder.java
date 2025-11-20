package br.com.skillshift.config;

import br.com.skillshift.recomendacao.model.RecomendacaoEntity;
import br.com.skillshift.recomendacao.repository.RecomendacaoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DataSeeder {

    @Inject
    RecomendacaoRepository repository;

    @PostConstruct
    @Transactional
    public void seed() {
        if (repository.count() > 0) {
            return;
        }

        List<Map<String, Object>> seeds = List.of(
                Map.of(
                        "titulo", "Transição para Frontend",
                        "descricao", "Trilha rápida para migrar para desenvolvimento frontend moderno.",
                        "area", "Tecnologia",
                        "senioridade", "Júnior",
                        "trilha", "Frontend",
                        "skills", List.of("HTML", "CSS", "JavaScript", "React"),
                        "link", "https://developer.mozilla.org/"
                ),
                Map.of(
                        "titulo", "Primeiros passos em Backend",
                        "descricao", "Fundamentos de APIs REST, Java e boas práticas de servidor.",
                        "area", "Tecnologia",
                        "senioridade", "Júnior",
                        "trilha", "Backend",
                        "skills", List.of("Java", "REST", "HTTP", "SQL"),
                        "link", "https://quarkus.io/"
                ),
                Map.of(
                        "titulo", "Dados para iniciantes",
                        "descricao", "Introdução a ETL, consultas e visualização de dados.",
                        "area", "Tecnologia",
                        "senioridade", "Pleno",
                        "trilha", "Dados",
                        "skills", List.of("Python", "SQL", "Pandas"),
                        "link", "https://www.kaggle.com/learn"
                )
        );

        seeds.forEach(seed -> {
            RecomendacaoEntity entity = new RecomendacaoEntity();
            entity.setTitulo((String) seed.get("titulo"));
            entity.setDescricao((String) seed.get("descricao"));
            entity.setArea((String) seed.get("area"));
            entity.setSenioridade((String) seed.get("senioridade"));
            entity.setTrilha((String) seed.get("trilha"));
            @SuppressWarnings("unchecked")
            List<String> skills = (List<String>) seed.get("skills");
            entity.setSkillsList(skills);
            entity.setLink((String) seed.get("link"));
            repository.persist(entity);
        });
    }
}
