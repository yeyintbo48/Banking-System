package com.banking.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.banking.core.dtos.AccountResponse;
import com.banking.core.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor 
@Tag(name = "Account API",description = "Core banking account operations")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Get account details",description = "Fetches account details and current balance by account number.")
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber) {
        AccountResponse response = accountService.getAccountDetails(accountNumber);
        return ResponseEntity.ok(response);
    }
    
}
