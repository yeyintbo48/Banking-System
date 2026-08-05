package com.banking.core.dtos;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransferRequest(
    @NotBlank(message = "Receiver account name is required")
    String toAccountNumber,

    @NotBlank(message = "Sender account name is required")
    String fromAccountNumber,

    @NotNull(message = "Amount is required")
    @Positive(message = "Trnasfer amount must be greater than zero")
    BigDecimal amount,

    @NotBlank(message = "ReferenceId(Idempontency)key is required")
    String referenceId,
    
    String description
) {}
