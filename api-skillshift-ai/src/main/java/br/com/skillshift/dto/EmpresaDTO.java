package br.com.skillshift.dto;

import br.com.skillshift.model.TamanhoEmpresa;

public class EmpresaDTO {
    private String nome;
    private String setor;
    private TamanhoEmpresa tamanho;
    private String cnpj;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public TamanhoEmpresa getTamanho() {
        return tamanho;
    }

    public void setTamanho(TamanhoEmpresa tamanho) {
        this.tamanho = tamanho;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
