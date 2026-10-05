package com.banking.core.dtos;

import java.math.BigDecimal;

public record AccountResponse(
    String accountNumber,
    String accountHolderName,
    BigDecimal balance,
    String currency,
    String status
) {}
