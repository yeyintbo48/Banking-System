package com.banking.core.dtos;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record TransactionHistoryResponse(
    Long transactionId,
    String referenceId,
    String entryType,
    BigDecimal amount,
    ZonedDateTime createdAt
) {}
