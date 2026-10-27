package com.banking.core;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import com.banking.core.dtos.TransferRequest;
import com.banking.core.entity.Account;
import com.banking.core.entity.Transaction;
import com.banking.core.enums.AccountStatus;
import com.banking.core.exception.DuplicateTransactionException;
import com.banking.core.exception.InsufficientBalanceException;
import com.banking.core.repository.AccountRepository;
import com.banking.core.repository.JournalEntryRepository;
import com.banking.core.repository.TransactionRepository;
import com.banking.core.service.TransferServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @Mock
    private  AccountRepository accountRepository;

    @Mock
    private  TransactionRepository transactionRepository;

    @Mock
    private  JournalEntryRepository journalEntryRepository;

    @InjectMocks
    private TransferServiceImpl TransferService;
    private Account senderAccount;
    private Account receiverAccount;
    private TransferRequest request;

    @BeforeEach
    void setup(){
        senderAccount = Account.builder()
            .id(1L)
            .accountNumber("ACC-001")
            .balance(new BigDecimal("10000.00"))
            .build();

        receiverAccount = Account.builder()
            .id(2L)
            .accountNumber("ACC-002")
            .balance(new BigDecimal("5000.00"))
            .build();

        request = new TransferRequest(
            "ACC-002",
            "ACC-001",
            new BigDecimal("2000.0"),
            UUID.randomUUID().toString(),
            "Payment For Services"
        );
    }

    @Test
    void shouldTransferSuccessfully(){
        //Arrange
        when(accountRepository.findByAccountNumber("ACC-001")).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByAccountNumber("ACC-002")).thenReturn(Optional.of(receiverAccount));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        //Act
        Transaction result = TransferService.transfer(request);

        //Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("8000.00"),senderAccount.getBalance());
        assertEquals(new BigDecimal("7000.00"),receiverAccount.getBalance());

        verify(accountRepository,times(2)).save(any(Account.class));
        verify(journalEntryRepository,times(1)).saveAll(anyList());
    }

    @Test
    void shouldThrowExceptionWhenInsufficientBalance(){
        //Arrange
        senderAccount.setBalance(new BigDecimal("1000.00"));
        when(transactionRepository.findByReferenceId(request.referenceId())).thenReturn(Optional.empty());
        when(accountRepository.findByAccountNumber("ACC-001")).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByAccountNumber("ACC-002")).thenReturn(Optional.of(receiverAccount));

        //Act&Assert
        Exception exception = assertThrows(InsufficientBalanceException.class,() ->{
            TransferService.transfer(request);
        });

        assertTrue(exception.getMessage().contains("Insufficient Balance in account: ACC-001"));
        verify(transactionRepository,never()).save(any(Transaction.class));
        verify(journalEntryRepository,never()).saveAll(anyList());
    }

    @Test 
    void shouldThrowExceptionWhenSelfTrnasfer(){
        //Arrange
        TransferRequest selfTransferRequest = new TransferRequest(
            "ACC:001", 
            "ACC:001", 
            new BigDecimal("500.00"), 
            UUID.randomUUID().toString(), 
            "Self transfer attempt");

            //Act & Assert
            assertThrows(IllegalArgumentException.class,() ->{
                TransferService.transfer(selfTransferRequest);
            });

            verify(accountRepository,never()).findByAccountNumber(any());
            verify(transactionRepository,never()).save(any(Transaction.class));
    }

    @Test 
    void shouldThrowExceptionWhenDuplicateReferenceId(){
        //Arrange
        when(transactionRepository.findByReferenceId(request.referenceId()))
        .thenReturn(Optional.of(Transaction.builder().referenceId(request.referenceId()).build()));

        //Act & Assert
        assertThrows(DuplicateTransactionException.class,() ->{
            TransferService.transfer(request);
        });

        verify(accountRepository,never()).findByAccountNumber(any());
        verify(transactionRepository,never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenSenderAccountIsFrozen(){
        //Arrange
        senderAccount.setStatus(AccountStatus.FROZEN);
        when(transactionRepository.findByReferenceId(request.referenceId())).thenReturn(Optional.empty());
        when(accountRepository.findByAccountNumber("ACC-001")).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByAccountNumber("ACC-002")).thenReturn(Optional.of(receiverAccount));

        //Act & Assert
        assertThrows(IllegalStateException.class,() ->{
            TransferService.transfer(request);
        });

        verify(transactionRepository,never()).save(any(Transaction.class));
        verify(journalEntryRepository,never()).saveAll(anyList());
    }
}
