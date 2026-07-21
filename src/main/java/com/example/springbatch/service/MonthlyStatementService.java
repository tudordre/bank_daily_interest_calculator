package com.example.springbatch.service;

import com.example.springbatch.domain.Account;
import com.example.springbatch.domain.Statement;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class MonthlyStatementService {

    public Statement generateMonthlyStatement(Account account, LocalDate referenceDate) {
        Statement statement = new Statement();
        statement.setAccount(account);
        statement.setMonth(referenceDate.withDayOfMonth(1).toString().substring(0, 7));
        statement.setOpeningBalance(account.getBalance());
        statement.setClosingBalance(account.getBalance());
        statement.setGeneratedAt(referenceDate);
        return statement;
    }
}
