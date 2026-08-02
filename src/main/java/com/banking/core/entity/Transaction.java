package com.banking.core.entity;

import java.time.ZonedDateTime;
import org.hibernate.annotations.CreationTimestamp;
import com.banking.core.enums.TransactionStatus;
import com.banking.core.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_id",nullable = false,length = 36,unique = true)
    private String referenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type",nullable = false,length = 30)
    private TransactionType transactionType;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    @Builder.Default
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private ZonedDateTime createdAt;
}
