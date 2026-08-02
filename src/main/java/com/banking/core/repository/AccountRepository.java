package com.banking.core.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.core.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long>{
    Optional<Account> findByAccountNumber(String accountNumber);
}
