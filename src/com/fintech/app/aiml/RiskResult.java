package com.fintech.app.aiml;

public class RiskResult {
    private double riskScore;
    private boolean risky;
    private String reason;

    public RiskResult(double riskScore, boolean risky, String reason) {
        this.riskScore = riskScore;
        this.risky = risky;
        this.reason = reason;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public boolean isRisky() {
        return risky;
    }

    public String getReason() {
        return reason;
    }
}