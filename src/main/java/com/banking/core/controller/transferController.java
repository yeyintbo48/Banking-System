package com.banking.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.banking.core.dtos.TransferRequest;
import com.banking.core.dtos.TransferResponse;
import com.banking.core.entity.Transaction;
import com.banking.core.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
@Tag(name = "Transfer API",description = "Core banking fund transfer operations")
public class transferController {
    private final TransferService transferService;

    @Operation(summary = "Initiate a fund transfer",description = "Transfers money between two accounts using a double-entry ledger entry system.")
    @ApiResponses(value = {
              @ApiResponse(responseCode = "201",description = "Transfer created Successfully."),
              @ApiResponse(responseCode = "400",description = "Invalid request or insufficient balance."),
              @ApiResponse(responseCode = "404",description = "Account not found"),
              @ApiResponse(responseCode = "409",description = "Duplicate Transaction or currency conflict.")
    })

    @PostMapping
    public ResponseEntity<TransferResponse> initiateTransfer(@Valid @RequestBody TransferRequest request) {
      Transaction transaction = transferService.transfer(request);
      
      TransferResponse response = new TransferResponse(
        transaction.getReferenceId(),
        transaction.getStatus().name(),
        "Transfer request successfully"
      );

      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } 
}
