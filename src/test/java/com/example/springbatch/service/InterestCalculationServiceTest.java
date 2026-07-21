package com.example.springbatch.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbatch.domain.Account;
import com.example.springbatch.domain.AccountStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class InterestCalculationServiceTest {

    private final InterestCalculationService service = new InterestCalculationService();

    @Test
    void shouldCalculateDailyInterestForActiveAccount() {
        Account account = new Account();
        account.setBalance(new BigDecimal("1000.00"));
        account.setInterestRate(new BigDecimal("0.05"));
        account.setStatus(AccountStatus.ACTIVE);

        BigDecimal interest = service.calculateDailyInterest(account, LocalDate.now());

        assertThat(interest).isEqualByComparingTo("0.1370");
    }
}
