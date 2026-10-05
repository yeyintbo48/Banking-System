package com.banking.core.service;

import java.util.List;
import com.banking.core.dtos.AccountResponse;
import com.banking.core.dtos.TransactionHistoryResponse;

public interface AccountService {
    AccountResponse getAccountDetails(String accountNumber);

    List<TransactionHistoryResponse> getTransactionHistory(String accountNumber);
}
