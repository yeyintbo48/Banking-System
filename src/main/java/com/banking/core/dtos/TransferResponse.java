package com.banking.core.dtos;

public record TransferResponse(
    String referenceId,
    String status,
    String message
) {}
