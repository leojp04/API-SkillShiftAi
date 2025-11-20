package br.com.skillshift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "recomendacoes")
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recomendacao")
    private Long idRecomendacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_curso")
    private Curso curso;

    private BigDecimal score;

    @Enumerated(EnumType.STRING)
    private FonteRecomendacao fonte;

    @Enumerated(EnumType.STRING)
    private StatusRecomendacao status;

    @Column(name = "data_recomendacao")
    private LocalDate dataRecomendacao;

    private String cluster;

    @Column(name = "payload_ia", columnDefinition = "CLOB")
    private String payloadIa;

    public Long getIdRecomendacao() {
        return idRecomendacao;
    }

    public void setIdRecomendacao(Long idRecomendacao) {
        this.idRecomendacao = idRecomendacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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
