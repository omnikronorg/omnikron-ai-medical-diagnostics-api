package org.omnikron.aimda.model;

public class PredictionResult {
    private String prediction;
    private double probabilityPneumonia;
    
    public PredictionResult()
    {

    }

    public PredictionResult(String prediction, double probabilityPneumonia)
    {
        this.prediction = prediction;
        this.probabilityPneumonia = probabilityPneumonia;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public double getProbabilityPneumonia() {
        return probabilityPneumonia;
    }

    public void setProbabilityPneumonia(double probabilityPneumonia) {
        this.probabilityPneumonia = probabilityPneumonia;
    }
    
}
