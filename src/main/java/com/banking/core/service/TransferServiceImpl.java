package com.banking.core.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.banking.core.entity.Account;
import com.banking.core.entity.JournalEntry;
import com.banking.core.entity.Transaction;
import com.banking.core.enums.EntryType;
import com.banking.core.enums.TransactionStatus;
import com.banking.core.enums.TransactionType;
import com.banking.core.enums.TransferRequest;
import com.banking.core.exception.AccountNotFoundException;
import com.banking.core.exception.InsufficientBalanceException;
import com.banking.core.repository.AccountRepository;
import com.banking.core.repository.JournalEntryRepository;
import com.banking.core.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService{
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final JournalEntryRepository journalEntryRepository;

    @Override
    @Transactional
    public Transaction transfer(TransferRequest request){
        Account fromAccount = accountRepository.findByAccountNumber(request.fromAccountNumber())
            .orElseThrow(()-> new AccountNotFoundException("Sender Account Not found" + request.fromAccountNumber()));

        Account toAccount = accountRepository.findByAccountNumber(request.toAccountNumber())
            .orElseThrow(()-> new AccountNotFoundException("Receiver Account Not found:" + request.toAccountNumber()));

        if(fromAccount.getBalance().compareTo(request.amount())< 0){
            throw new InsufficientBalanceException("Insufficient Balance in account:" + fromAccount.getAccountNumber());
        }

        Transaction transaction = Transaction.builder()
            .referenceId(request.referenceId())
            .transactionType(TransactionType.TRANSFER)
            .status(TransactionStatus.SUCCESS)
            .description(request.description())
            .build();
        transaction = transactionRepository.save(transaction);

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.amount()));
        toAccount.setBalance(toAccount.getBalance().add(request.amount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        JournalEntry debitEntry = JournalEntry.builder()
            .transaction(transaction)
            .account(fromAccount)
            .entryType(EntryType.DEBIT)
            .amount(request.amount())
            .build();

        JournalEntry creditEntry = JournalEntry.builder()
            .transaction(transaction)
            .account(toAccount)
            .entryType(EntryType.CREDIT)
            .amount(request.amount())
            .build();

        journalEntryRepository.saveAll(List.of(debitEntry,creditEntry));

        return transaction;
    }
}
