package com.banking.core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.core.entity.JournalEntry;

public interface JournalEntryRepository extends JpaRepository<JournalEntry,Long>{
    List<JournalEntry> findByTransactionId(Long transactionId);
    @EntityGraph (attributePaths = {"transaction"})
    List<JournalEntry> findByAccountId(Long accountId);
}
