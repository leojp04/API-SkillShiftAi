package br.com.skillshift.dto;

import java.util.List;

public class IAProfileResponse {
    private Integer cluster;
    private List<String> cursosRecomendados;

    public Integer getCluster() {
        return cluster;
    }

    public void setCluster(Integer cluster) {
        this.cluster = cluster;
    }

    public List<String> getCursosRecomendados() {
        return cursosRecomendados;
    }

    public void setCursosRecomendados(List<String> cursosRecomendados) {
        this.cursosRecomendados = cursosRecomendados;
    }
}
