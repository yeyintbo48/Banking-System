package com.banking.core.service;

import com.banking.core.dtos.AccountResponse;

public interface AccountService {
    AccountResponse getAccountDetails(String accountNumber);
}
