package com.fintech.app.aiml;

public class RiskEngine {

    public RiskResult analyseTransaction(double amount, String currency, int previousFailedAttempts) {

        double riskScore = 0.0;
        String reason = "Normal transaction pattern";

        if (amount > 50000) {
            riskScore += 0.5;
            reason = "High transaction amount";
        }

        if (!currency.equalsIgnoreCase("INR")) {
            riskScore += 0.2;
            reason = reason + " + cross-border currency";
        }

        if (previousFailedAttempts >= 3) {
            riskScore += 0.3;
            reason = reason + " + repeated failed attempts";
        }

        boolean risky = riskScore >= 0.7;

        return new RiskResult(riskScore, risky, reason);
    }
}