package com.fintech.app.engine;

import com.fintech.app.dto.TransactionRequestDTO;
import com.fintech.app.dto.TransactionResponseDTO;
import com.fintech.app.enums.TransactionStatus;
import com.fintech.app.model.RiskAssessment;
import com.fintech.app.model.TransactionResult;
import com.fintech.app.service.RiskService;
import com.fintech.app.service.TransactionServiceJdbc;
import com.fintech.app.service.ValidationServiceJdbc;

public class TransactionEngineJdbc {

    private final ValidationServiceJdbc validationService;
    private final RiskService riskService;
    private final TransactionServiceJdbc transactionService;

    public TransactionEngineJdbc(ValidationServiceJdbc validationService,
                                 RiskService riskService,
                                 TransactionServiceJdbc transactionService) {
        this.validationService = validationService;
        this.riskService = riskService;
        this.transactionService = transactionService;
    }

    public TransactionResponseDTO execute(TransactionRequestDTO request) {
        validationService.validateTransaction(request);

        RiskAssessment riskAssessment = riskService.assessRisk(request);
        if (!riskAssessment.isAllowed()) {
            return new TransactionResponseDTO(
                    null,
                    TransactionStatus.BLOCKED,
                    riskAssessment.getMessage(),
                    0.0,
                    0.0
            );
        }

        TransactionResult result = transactionService.processTransaction(request);

        return new TransactionResponseDTO(
                result.getTransaction().getTransactionId(),
                result.getTransaction().getStatus(),
                "Transaction completed successfully.",
                request.getAmount(),
                result.getCreditedAmount()
        );
    }
}