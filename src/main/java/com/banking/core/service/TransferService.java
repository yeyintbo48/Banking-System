package com.banking.core.service;

import com.banking.core.dtos.TransferRequest;
import com.banking.core.entity.Transaction;

public interface TransferService {
    Transaction transfer(TransferRequest request);
}
