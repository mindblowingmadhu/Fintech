package com.fintech.app.model;

import com.fintech.app.enums.RiskLevel;

public class RiskAssessment {
    private RiskLevel riskLevel;
    private String message;
    private boolean allowed;

    public RiskAssessment() {
    }

    public RiskAssessment(RiskLevel riskLevel, String message, boolean allowed) {
        this.riskLevel = riskLevel;
        this.message = message;
        this.allowed = allowed;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
}