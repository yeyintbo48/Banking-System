package com.banking.core.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.banking.core.dtos.AccountResponse;
import com.banking.core.dtos.TransactionHistoryResponse;
import com.banking.core.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("/api/v1/accounts/{accountNumber}")
@RequiredArgsConstructor 
@Tag(name = "Account API",description = "Core banking account operations")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Get account details",description = "Fetches account details and current balance by account number.")
    @GetMapping
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber) {
        AccountResponse response = accountService.getAccountDetails(accountNumber);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get transaction history",description = "Fetches transaction history(debit & credit) for a specific account number.")
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactionHistory(@PathVariable String accountNumber) {
        List<TransactionHistoryResponse> history = accountService.getTransactionHistory(accountNumber);
        return ResponseEntity.ok(history);
    }
}
