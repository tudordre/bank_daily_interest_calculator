# Bank with Daily Interest Calculator

A simple bank application that calculates daily interest for active accounts and generates monthly account statements.

Built with Spring Boot, Spring Batch, Spring Data JPA, Liquibase, and H2.

## Features

- Daily interest batch job for active accounts
- Monthly statement generation batch job
- REST API for creating and retrieving accounts
- H2 in-memory database with Liquibase changelogs for schema and sample data
- Spring Batch job configuration with chunk processing

## Getting Started

### Prerequisites

- Java 21
- Maven 3.8+

### Build

```bash
mvn clean package
```

### Run

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080`.

### H2 Console

The H2 console is enabled at:

```
http://localhost:8080/h2-console
```

JDBC URL: `jdbc:h2:mem:springbatchdb`
Username: `sa`
Password: (empty)

## API Endpoints

- `GET /accounts` - List all accounts
- `POST /accounts` - Create a new account

Example request payload:

```json
{
  "accountNumber": "1234567890",
  "balance": 1000.00,
  "interestRate": 0.05,
  "status": "ACTIVE"
}
```

## Batch Jobs

Two batch jobs are configured in the application:

- `dailyInterestJob` - calculates daily interest for active accounts
- `monthlyStatementJob` - generates monthly statements for all accounts

> Note: `spring.batch.job.enabled` is set to `false` in `application.yml`, so jobs are not started automatically by the framework.

## Project Structure

- `src/main/java` - application source code
- `src/main/resources` - application configuration and Liquibase changelogs
- `src/test/java` - unit tests

## Notes

The project uses Spring Boot 3.3.3, Spring Batch, Spring Data JPA, and Liquibase with an H2 in-memory database.