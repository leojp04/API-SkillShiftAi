package br.com.skillshift.bo;

import br.com.skillshift.client.IAClient;
import br.com.skillshift.dao.CursoDAO;
import br.com.skillshift.dao.RecomendacaoDAO;
import br.com.skillshift.dao.RecomendacaoLogDAO;
import br.com.skillshift.dao.UsuarioDAO;
import br.com.skillshift.dto.IAProfileRequest;
import br.com.skillshift.dto.IAProfileResponse;
import br.com.skillshift.dto.RecomendacaoDTO;
import br.com.skillshift.exception.BadRequestException;
import br.com.skillshift.exception.IAServiceIndisponivelException;
import br.com.skillshift.exception.NotFoundException;
import br.com.skillshift.model.Curso;
import br.com.skillshift.model.FonteRecomendacao;
import br.com.skillshift.model.NivelCurso;
import br.com.skillshift.model.Recomendacao;
import br.com.skillshift.model.RecomendacaoLog;
import br.com.skillshift.model.StatusRecomendacao;
import br.com.skillshift.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class RecomendacaoBO {

    @Inject
    RecomendacaoDAO recomendacaoDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    CursoDAO cursoDAO;

    @Inject
    RecomendacaoLogDAO recomendacaoLogDAO;

    @Inject
    @RestClient
    IAClient iaClient;

    public List<Recomendacao> listarTodos() {
        return recomendacaoDAO.listarTodos();
    }

    public List<Recomendacao> listarPorUsuario(Long usuarioId) {
        validarUsuario(usuarioId);
        return recomendacaoDAO.buscarPorUsuario(usuarioId);
    }

    public Recomendacao buscarPorId(Long id) {
        return recomendacaoDAO.buscarPorId(id).orElseThrow(() -> new NotFoundException("Recomendacao nao encontrada"));
    }

    @Transactional
    public Recomendacao criar(RecomendacaoDTO dto) {
        validarCriacao(dto);
        Usuario usuario = validarUsuario(dto.getUsuarioId());
        Curso curso = validarCurso(dto.getCursoId());

        Recomendacao recomendacao = new Recomendacao();
        recomendacao.setUsuario(usuario);
        recomendacao.setCurso(curso);
        recomendacao.setScore(dto.getScore() != null ? dto.getScore() : BigDecimal.ONE);
        recomendacao.setFonte(dto.getFonte() != null ? dto.getFonte() : FonteRecomendacao.MANUAL);
        recomendacao.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusRecomendacao.PENDENTE);
        recomendacao.setDataRecomendacao(dto.getDataRecomendacao() != null ? dto.getDataRecomendacao() : LocalDate.now());
        recomendacao.setCluster(dto.getCluster());
        recomendacao.setPayloadIa(dto.getPayloadIa());

        Recomendacao salva = recomendacaoDAO.criar(recomendacao);
        registrarLog(salva, "Recomendacao criada manualmente");
        return salva;
    }

    @Transactional
    public Recomendacao atualizar(Long id, RecomendacaoDTO dto) {
        Recomendacao existente = buscarPorId(id);
        validarCriacao(dto);
        Usuario usuario = validarUsuario(dto.getUsuarioId());
        Curso curso = validarCurso(dto.getCursoId());

        existente.setUsuario(usuario);
        existente.setCurso(curso);
        if (dto.getScore() != null) {
            existente.setScore(dto.getScore());
        }
        if (dto.getFonte() != null) {
            existente.setFonte(dto.getFonte());
        }
        if (dto.getStatus() != null) {
            existente.setStatus(dto.getStatus());
        }
        existente.setDataRecomendacao(dto.getDataRecomendacao() != null ? dto.getDataRecomendacao() : existente.getDataRecomendacao());
        existente.setCluster(dto.getCluster());
        existente.setPayloadIa(dto.getPayloadIa());

        Recomendacao atualizada = recomendacaoDAO.atualizar(existente);
        registrarLog(atualizada, "Recomendacao atualizada");
        return atualizada;
    }

    @Transactional
    public void deletar(Long id) {
        buscarPorId(id);
        recomendacaoDAO.deletar(id);
    }

    @Transactional
    public List<Recomendacao> gerarRecomendacoes(Long usuarioId) {
        Usuario usuario = validarUsuario(usuarioId);
        IAProfileResponse resposta = chamarIA(usuario);
        List<String> recomendados = resposta.getCursosRecomendados() != null ? resposta.getCursosRecomendados() : List.of();

        List<Recomendacao> geradas = new ArrayList<>();
        for (String nomeCurso : recomendados) {
            Curso curso = cursoDAO.buscarPorNome(nomeCurso)
                    .orElseGet(() -> criarCursoSugerido(nomeCurso));

            RecomendacaoDTO dto = new RecomendacaoDTO();
            dto.setUsuarioId(usuarioId);
            dto.setCursoId(curso.getIdCurso());
            dto.setFonte(FonteRecomendacao.IA);
            dto.setStatus(StatusRecomendacao.EM_ANDAMENTO);
            dto.setDataRecomendacao(LocalDate.now());
            dto.setCluster(resposta.getCluster() != null ? resposta.getCluster().toString() : "0");
            dto.setPayloadIa(toJsonPayload(resposta));
            dto.setScore(BigDecimal.valueOf(0.75));
            geradas.add(criar(dto));
        }
        if (geradas.isEmpty()) {
            registrarLog(null, "IA retornou lista vazia para usuario " + usuarioId);
        }
        return geradas;
    }

    private IAProfileResponse chamarIA(Usuario usuario) {
        try {
            IAProfileRequest request = new IAProfileRequest();
            request.setOpScore(0.82);
            request.setCoScore(0.76);
            double idadeNormalizada = usuario.getIdade() != null ? usuario.getIdade() / 100.0 : 0.5;
            request.setLearningScore(idadeNormalizada);
            request.setTechAffinity(0.70);
            return iaClient.gerarCluster(request);
        } catch (Exception e) {
            throw new IAServiceIndisponivelException("Servico de IA indisponivel: " + e.getMessage());
        }
    }

    private void validarCriacao(RecomendacaoDTO dto) {
        if (dto == null) {
            throw new BadRequestException("Dados da recomendacao sao obrigatorios");
        }
        if (dto.getUsuarioId() == null) {
            throw new BadRequestException("Usuario e obrigatorio");
        }
        if (dto.getCursoId() == null) {
            throw new BadRequestException("Curso e obrigatorio");
        }
        if (dto.getFonte() != null && dto.getFonte() == FonteRecomendacao.IA && dto.getPayloadIa() == null) {
            throw new BadRequestException("Payload da IA deve ser informado quando a fonte e IA");
        }
    }

    private Usuario validarUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new BadRequestException("Usuario e obrigatorio");
        }
        return usuarioDAO.buscarPorId(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));
    }

    private Curso validarCurso(Long cursoId) {
        return cursoDAO.buscarPorId(cursoId)
                .orElseThrow(() -> new NotFoundException("Curso nao encontrado"));
    }

    private Curso criarCursoSugerido(String nomeCurso) {
        Curso curso = new Curso();
        curso.setNome(nomeCurso);
        curso.setCategoria("SUGESTAO_IA");
        curso.setDuracaoHoras(12);
        curso.setPlataforma("IA");
        curso.setNivel(NivelCurso.INTERMEDIARIO);
        curso.setAtivo(true);
        return cursoDAO.criar(curso);
    }

    private void registrarLog(Recomendacao recomendacao, String mensagem) {
        RecomendacaoLog log = new RecomendacaoLog();
        log.setRecomendacao(recomendacao);
        log.setMensagem(mensagem);
        log.setCriadoEm(LocalDateTime.now());
        recomendacaoLogDAO.criar(log);
    }

    private String toJsonPayload(IAProfileResponse resposta) {
        String cursos = resposta.getCursosRecomendados() == null
                ? "[]"
                : resposta.getCursosRecomendados().stream()
                .map(c -> "\"" + c.replace("\"", "") + "\"")
                .collect(Collectors.joining(",", "[", "]"));
        Integer cluster = resposta.getCluster();
        return "{\"cluster\":" + (cluster == null ? "null" : cluster) + ",\"cursosRecomendados\":" + cursos + "}";
    }
}
