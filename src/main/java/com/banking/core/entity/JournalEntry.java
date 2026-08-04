package com.banking.core.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.hibernate.annotations.CreationTimestamp;
import com.banking.core.enums.EntryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "journal_entries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "account_id",nullable = false,updatable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "transaction_id",nullable = false,updatable = false)
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type",nullable = false,updatable = false,length = 10)
    private EntryType entryType;

    @Column(nullable = false,precision = 19,scale = 4,updatable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private ZonedDateTime createdAt;
}
