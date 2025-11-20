package br.com.skillshift.bo;

import br.com.skillshift.dao.EmpresaDAO;
import br.com.skillshift.dto.EmpresaDTO;
import br.com.skillshift.exception.BadRequestException;
import br.com.skillshift.exception.NotFoundException;
import br.com.skillshift.model.Empresa;
import br.com.skillshift.model.TamanhoEmpresa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EmpresaBO {

    @Inject
    EmpresaDAO empresaDAO;

    public List<Empresa> listarTodos() {
        return empresaDAO.listarTodos();
    }

    public Empresa buscarPorId(Long id) {
        return empresaDAO.buscarPorId(id).orElseThrow(() -> new NotFoundException("Empresa nao encontrada"));
    }

    public Empresa criar(EmpresaDTO dto) {
        validar(dto);
        Empresa empresa = toEntity(dto, new Empresa());
        return empresaDAO.criar(empresa);
    }

    public Empresa atualizar(Long id, EmpresaDTO dto) {
        Empresa existente = buscarPorId(id);
        validar(dto);
        Empresa atualizado = toEntity(dto, existente);
        return empresaDAO.atualizar(atualizado);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        empresaDAO.deletar(id);
    }

    private void validar(EmpresaDTO dto) {
        if (dto == null) {
            throw new BadRequestException("Dados da empresa sao obrigatorios");
        }
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new BadRequestException("Nome da empresa obrigatorio");
        }
        if (dto.getCnpj() == null || dto.getCnpj().isBlank()) {
            throw new BadRequestException("CNPJ obrigatorio");
        }
        if (dto.getTamanho() == null || !isTamanhoValido(dto.getTamanho())) {
            throw new BadRequestException("Tamanho de empresa invalido");
        }
    }

    private Empresa toEntity(EmpresaDTO dto, Empresa empresa) {
        empresa.setNome(dto.getNome());
        empresa.setSetor(dto.getSetor());
        empresa.setTamanho(dto.getTamanho());
        empresa.setCnpj(dto.getCnpj());
        return empresa;
    }

    private boolean isTamanhoValido(TamanhoEmpresa tamanho) {
        return tamanho == TamanhoEmpresa.PEQUENA || tamanho == TamanhoEmpresa.MEDIA || tamanho == TamanhoEmpresa.GRANDE;
    }
}
