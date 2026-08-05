package com.banking.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.banking.core.dtos.TransferRequest;
import com.banking.core.dtos.TransferResponse;
import com.banking.core.entity.Transaction;
import com.banking.core.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class transferController {
    private final TransferService transferService;

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
