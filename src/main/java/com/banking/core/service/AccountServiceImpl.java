package com.banking.core.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.banking.core.dtos.AccountResponse;
import com.banking.core.dtos.TransactionHistoryResponse;
import com.banking.core.entity.Account;
import com.banking.core.entity.JournalEntry;
import com.banking.core.exception.AccountNotFoundException;
import com.banking.core.repository.AccountRepository;
import com.banking.core.repository.JournalEntryRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final JournalEntryRepository journalEntryRepository;

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

    @Transactional(readOnly = true)
    @Override 
    public List<TransactionHistoryResponse> getTransactionHistory(String accountNumber){
        Account account = accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new AccountNotFoundException("Account not found:" + accountNumber));

        List<JournalEntry> entries = journalEntryRepository.findByAccountId(account.getId());

        return entries.stream()
            .map(entry -> new TransactionHistoryResponse(
                entry.getTransaction().getId(),
                entry.getTransaction().getReferenceId(),
                entry.getEntryType().name(),
                entry.getAmount(),
                entry.getCreatedAt()
            ))
            .collect(Collectors.toList());
    }
}
