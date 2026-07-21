package com.example.springbatch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "statements")
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private String month;

    @Column(nullable = false)
    private java.math.BigDecimal openingBalance = java.math.BigDecimal.ZERO;

    @Column(nullable = false)
    private java.math.BigDecimal closingBalance = java.math.BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDate generatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public java.math.BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(java.math.BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public java.math.BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(java.math.BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
    }

    public LocalDate getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDate generatedAt) {
        this.generatedAt = generatedAt;
    }
}
