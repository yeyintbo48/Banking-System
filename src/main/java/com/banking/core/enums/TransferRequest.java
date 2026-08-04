package com.banking.core.enums;

import java.math.BigDecimal;

public record TransferRequest(
    String toAccountNumber,
    String fromAccountNumber,
    BigDecimal amount,
    String referenceId,
    String description
) {}
