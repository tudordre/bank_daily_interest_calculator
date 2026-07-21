package com.example.springbatch.service;

import com.example.springbatch.domain.Account;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class InterestCalculationService {

    public BigDecimal calculateDailyInterest(Account account, LocalDate date) {
        if (account == null || account.getBalance() == null || account.getInterestRate() == null) {
            return BigDecimal.ZERO;
        }

        return account.getBalance()
                .multiply(account.getInterestRate())
                .divide(BigDecimal.valueOf(365), 4, RoundingMode.HALF_UP);
    }
}
