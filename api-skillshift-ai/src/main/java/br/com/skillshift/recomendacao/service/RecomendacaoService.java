package br.com.skillshift.recomendacao.service;

import br.com.skillshift.recomendacao.dto.RecomendacaoRequest;
import br.com.skillshift.recomendacao.dto.RecomendacaoResponse;
import br.com.skillshift.recomendacao.model.RecomendacaoEntity;
import br.com.skillshift.recomendacao.repository.RecomendacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class RecomendacaoService {

    @Inject
    RecomendacaoRepository repository;

    public List<RecomendacaoResponse> listar(String area, String senioridade) {
        return repository.listarFiltrado(area, senioridade)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RecomendacaoResponse buscarPorId(UUID id) {
        RecomendacaoEntity entity = repository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Recomendacao nao encontrada"));
        return toResponse(entity);
    }

    @Transactional
    public RecomendacaoResponse criar(@Valid RecomendacaoRequest request) {
        validarSkills(request.getSkills());
        RecomendacaoEntity entity = new RecomendacaoEntity();
        preencherCampos(entity, request);
        repository.persist(entity);
        return toResponse(entity);
    }

    @Transactional
    public RecomendacaoResponse atualizar(UUID id, @Valid RecomendacaoRequest request) {
        RecomendacaoEntity entity = repository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Recomendacao nao encontrada"));
        validarSkills(request.getSkills());
        preencherCampos(entity, request);
        entity.setAtualizadoEm(OffsetDateTime.now());
        return toResponse(entity);
    }

    @Transactional
    public void deletar(UUID id) {
        boolean removido = repository.deleteById(id);
        if (!removido) {
            throw new NotFoundException("Recomendacao nao encontrada");
        }
    }

    private void preencherCampos(RecomendacaoEntity entity, RecomendacaoRequest request) {
        entity.setTitulo(request.getTitulo());
        entity.setDescricao(request.getDescricao());
        entity.setArea(request.getArea());
        entity.setSenioridade(request.getSenioridade());
        entity.setTrilha(request.getTrilha());
        entity.setSkillsList(request.getSkills());
        entity.setLink(request.getLink());
    }

    private RecomendacaoResponse toResponse(RecomendacaoEntity entity) {
        RecomendacaoResponse response = new RecomendacaoResponse();
        response.setId(entity.getId());
        response.setTitulo(entity.getTitulo());
        response.setDescricao(entity.getDescricao());
        response.setArea(entity.getArea());
        response.setSenioridade(entity.getSenioridade());
        response.setTrilha(entity.getTrilha());
        response.setSkills(entity.getSkillsList());
        response.setLink(entity.getLink());
        response.setCriadoEm(entity.getCriadoEm());
        response.setAtualizadoEm(entity.getAtualizadoEm());
        return response;
    }

    private void validarSkills(List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            throw new IllegalArgumentException("Skills devem ter ao menos 1 item");
        }
        boolean invalida = skills.stream().anyMatch(s -> s == null || s.isBlank());
        if (invalida) {
            throw new IllegalArgumentException("Skills nao podem ser vazias");
        }
    }
}
