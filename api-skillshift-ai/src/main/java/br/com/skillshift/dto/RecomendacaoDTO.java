package br.com.skillshift.dto;

import br.com.skillshift.model.FonteRecomendacao;
import br.com.skillshift.model.StatusRecomendacao;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RecomendacaoDTO {
    private Long usuarioId;
    private Long cursoId;
    private BigDecimal score;
    private FonteRecomendacao fonte;
    private StatusRecomendacao status;
    private LocalDate dataRecomendacao;
    private String cluster;
    private String payloadIa;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public FonteRecomendacao getFonte() {
        return fonte;
    }

    public void setFonte(FonteRecomendacao fonte) {
        this.fonte = fonte;
    }

    public StatusRecomendacao getStatus() {
        return status;
    }

    public void setStatus(StatusRecomendacao status) {
        this.status = status;
    }

    public LocalDate getDataRecomendacao() {
        return dataRecomendacao;
    }

    public void setDataRecomendacao(LocalDate dataRecomendacao) {
        this.dataRecomendacao = dataRecomendacao;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getPayloadIa() {
        return payloadIa;
    }

    public void setPayloadIa(String payloadIa) {
        this.payloadIa = payloadIa;
    }
}
