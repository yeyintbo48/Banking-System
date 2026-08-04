package com.banking.core.service;

import com.banking.core.entity.Transaction;
import com.banking.core.enums.TransferRequest;

public interface TransferService {
    Transaction transfer(TransferRequest request);
}
