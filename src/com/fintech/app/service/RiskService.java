package com.fintech.app.service;

import com.fintech.app.dto.TransactionRequestDTO;
import com.fintech.app.enums.RiskLevel;
import com.fintech.app.model.RiskAssessment;

public class RiskService {

    public RiskAssessment assessRisk(TransactionRequestDTO request) {
        if (request.getAmount() > 100000) {
            return new RiskAssessment(RiskLevel.HIGH,
                    "High-risk transaction: amount exceeds threshold.", false);
        } else if (request.getAmount() > 50000) {
            return new RiskAssessment(RiskLevel.MEDIUM,
                    "Medium-risk transaction: manual review suggested.", true);
        } else {
            return new RiskAssessment(RiskLevel.LOW,
                    "Low-risk transaction.", true);
        }
    }
}