package br.com.skillshift.dto;

public class IAProfileRequest {
    private Double opScore;
    private Double coScore;
    private Double learningScore;
    private Double techAffinity;

    public Double getOpScore() {
        return opScore;
    }

    public void setOpScore(Double opScore) {
        this.opScore = opScore;
    }

    public Double getCoScore() {
        return coScore;
    }

    public void setCoScore(Double coScore) {
        this.coScore = coScore;
    }

    public Double getLearningScore() {
        return learningScore;
    }

    public void setLearningScore(Double learningScore) {
        this.learningScore = learningScore;
    }

    public Double getTechAffinity() {
        return techAffinity;
    }

    public void setTechAffinity(Double techAffinity) {
        this.techAffinity = techAffinity;
    }
}
