package com.banking.core.service;

import org.springframework.stereotype.Service;
import com.banking.core.dtos.AccountResponse;
import com.banking.core.entity.Account;
import com.banking.core.exception.AccountNotFoundException;
import com.banking.core.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Override 
    public AccountResponse getAccountDetails(String accountNumber){
        Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new AccountNotFoundException("Account not found:" + accountNumber));

        return new AccountResponse(
            account.getAccountNumber(),
            account.getAccountHolderName(),
            account.getBalance(),
            account.getCurrency(),
            account.getStatus().name()
        );
    }
}
