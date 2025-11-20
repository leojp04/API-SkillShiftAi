package br.com.skillshift.bo;

import br.com.skillshift.dao.CursoDAO;
import br.com.skillshift.dto.CursoDTO;
import br.com.skillshift.exception.BadRequestException;
import br.com.skillshift.exception.NotFoundException;
import br.com.skillshift.model.Curso;
import br.com.skillshift.model.NivelCurso;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CursoBO {

    @Inject
    CursoDAO cursoDAO;

    public List<Curso> listarTodos() {
        return cursoDAO.listarTodos();
    }

    public Curso buscarPorId(Long id) {
        return cursoDAO.buscarPorId(id).orElseThrow(() -> new NotFoundException("Curso nao encontrado"));
    }

    public Curso criar(CursoDTO dto) {
        validar(dto);
        Curso curso = toEntity(dto, new Curso());
        if (dto.getAtivo() == null) {
            curso.setAtivo(true);
        }
        return cursoDAO.criar(curso);
    }

    public Curso atualizar(Long id, CursoDTO dto) {
        Curso existente = buscarPorId(id);
        validar(dto);
        Curso curso = toEntity(dto, existente);
        if (dto.getAtivo() != null) {
            curso.setAtivo(dto.getAtivo());
        }
        return cursoDAO.atualizar(curso);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        cursoDAO.deletar(id);
    }

    private void validar(CursoDTO dto) {
        if (dto == null) {
            throw new BadRequestException("Dados do curso sao obrigatorios");
        }
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new BadRequestException("Nome do curso obrigatorio");
        }
        if (dto.getCategoria() == null || dto.getCategoria().isBlank()) {
            throw new BadRequestException("Categoria do curso obrigatoria");
        }
        if (dto.getNivel() == null || !isNivelValido(dto.getNivel())) {
            throw new BadRequestException("Nivel do curso invalido");
        }
    }

    private Curso toEntity(CursoDTO dto, Curso curso) {
        curso.setNome(dto.getNome());
        curso.setCategoria(dto.getCategoria());
        curso.setDuracaoHoras(dto.getDuracaoHoras());
        curso.setPlataforma(dto.getPlataforma());
        curso.setNivel(dto.getNivel());
        if (dto.getAtivo() != null) {
            curso.setAtivo(dto.getAtivo());
        }
        return curso;
    }

    private boolean isNivelValido(NivelCurso nivel) {
        return nivel == NivelCurso.INICIAL || nivel == NivelCurso.INTERMEDIARIO || nivel == NivelCurso.AVANCADO;
    }
}
