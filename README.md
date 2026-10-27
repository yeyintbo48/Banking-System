# 🏦 Core Banking Ledger System (Spring Boot)

A production-grade Core Banking API built with **Spring Boot 4**, focusing on **Data Integrity, Concurrency Control**, and **Zero-Sum Accuracy** using a Double-Entry Bookkeeping architecture.

Unlike typical CRUD applications, this project does not simply overwrite account balances. It maintains an **Immutable Ledger (Journal Entries)** to ensure every transaction is fully traceable, auditable, and mathematically accurate.

---

## 🚀 Core Features & Architectural Decisions

* **Double-Entry Bookkeeping:** Balances are managed through immutable `DEBIT` and `CREDIT` entries. The system enforces strict Zero-Sum validation before persisting transactions.
* **Concurrency & Race Condition Handling:** Utilizes Optimistic Locking (`@Version`) to prevent data corruption during simultaneous high-frequency transfers.
* **Idempotent API Design:** Transfer APIs accept a unique `referenceId` to ensure safe retries without risking duplicate deductions.
* **Global Error Handling:** Implements `ProblemDetail` (RFC 7807 specification) via `@RestControllerAdvice` for consistent and professional error responses.
* **Database Migration:** Managed by **Flyway**, ensuring version-controlled and reproducible database schemas.
* **Clean Layered Architecture:** Clearly separated into `controller`, `service`, `repository`, `entity`, and `dtos` layers for maintainability and testability.

---

## 🛠️ Tech Stack

* **Language:** Java 25
* **Framework:** Spring Boot 4.1.0
* **Data Access:** Spring Data JPA, Hibernate
* **Database:** PostgreSQL 16 (with Docker Compose)
* **Database Migration:** Flyway
* **API Documentation:** SpringDoc OpenAPI (Swagger UI)
* **Testing:** JUnit 5, Mockito
* **Build Tool:** Maven

---

## 🗄️ Database Schema Design (The Heart of the System)

The database strictly enforces the double-entry principle. The `journal_entries` table acts as the ultimate source of truth, and no row is ever updated or deleted.

* `accounts`: Stores account details and a cached snapshot of the current balance for fast reads.
* `transactions`: Represents the metadata/header of a financial event (e.g., Transfer, Deposit).
* `journal_entries`: The immutable ledger containing individual `DEBIT` and `CREDIT` movements.

---

## ⚡ Getting Started

### Prerequisites
* Java 25+ installed
* Maven installed
* Docker & Docker Compose (for PostgreSQL database)

### Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/yeyintbo48/core-banking-system-springboot.git](https://github.com/yeyintbo48/core-banking-system-springboot.git)
   cd core-banking-system-springboot
   ```

2. **Start the PostgreSQL Database:**
   Make sure Docker is running, then execute:
   ```bash
   docker-compose up -d
   ```

3. **Run the Application:**
   Flyway will automatically create the required database schemas on startup.
   ```bash
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

---

## 🧪 Testing

The project includes unit tests focusing on domain logic, balance calculations, and exception handling.

```bash
mvn test
```

---

## 📖 API Documentation

Once the application is running (with `springdoc` enabled in `application.yaml`), you can view and test the endpoints via Swagger UI at:
* **Swagger UI:** `http://localhost:8080/swagger-ui.html`

---

## 👨‍💻 Author

**Ye Yint Bo**
* Backend Developer passionate about building robust, scalable, and secure backend systems.
* GitHub: [https://github.com/yeyintbo48](https://github.com/yeyintbo48)