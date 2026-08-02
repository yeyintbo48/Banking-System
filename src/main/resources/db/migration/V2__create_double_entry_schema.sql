--Account Table
CREATE Table accounts(
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_holder_name VARCHAR(100) NOT NULL,
    balance NUMERIC(19,4) NOT NULL DEFAULT 0.0000,
    currency VARCHAR(3) NOT NULL DEFAULT 'MMK',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    version BIGINT NOT NULL DEFAULT 0, --For Optimistic Locking
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--Transaction Table (Header)
CREATE Table transactions(
    id BIGSERIAL PRIMARY KEY,
    reference_id VARCHAR(36) UNIQUE NOT NULL, --UUID For Idempotency
    transaction_type VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--Journal Entries Table (The Immutable Ledger)
CREATE Table journal_entries(
    id BIGSERIAL PRIMARY KEY,
    transaction_id BIGINT NOT NULL REFERENCES transactions(id),
    account_id BIGINT NOT NULL REFERENCES accounts(id),
    entry_type VARCHAR(10) NOT NULL, --DEBIT, CREDIT
    amount NUMERIC(19,4) NOT NULL CHECK(amount > 0), --Must be positive
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--Indexes For Performance
CREATE INDEX idx_accounts_account_number ON accounts(account_number);
CREATE INDEX idx_transactions_reference_id ON transactions(reference_id);
CREATE INDEX idx_journal_entries_tx_id ON journal_entries(transaction_id);
CREATE INDEX idx_journal_entries_acc_id ON journal_entries(account_id);