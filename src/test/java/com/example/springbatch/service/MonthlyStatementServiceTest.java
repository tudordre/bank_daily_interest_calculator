package com.example.springbatch.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbatch.domain.Account;
import com.example.springbatch.domain.AccountStatus;
import com.example.springbatch.domain.Statement;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MonthlyStatementServiceTest {

    private final MonthlyStatementService service = new MonthlyStatementService();

    @Test
    void shouldGenerateStatementForPreviousMonth() {
        Account account = new Account();
        account.setBalance(new BigDecimal("1200.00"));
        account.setInterestRate(new BigDecimal("0.05"));
        account.setStatus(AccountStatus.ACTIVE);

        Statement statement = service.generateMonthlyStatement(account, LocalDate.of(2026, 6, 1));

        assertThat(statement.getAccount()).isEqualTo(account);
        assertThat(statement.getMonth()).isEqualTo("2026-06");
        assertThat(statement.getClosingBalance()).isEqualByComparingTo("1200.00");
    }
}
