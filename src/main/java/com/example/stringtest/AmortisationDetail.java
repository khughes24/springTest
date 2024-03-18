package com.example.stringtest;

import java.math.BigDecimal;

public class AmortisationDetail {
    public String amLineId;

    public String getAmLineId() {
        return amLineId;
    }

    public void setAmLineId(String amLineId) {
        this.amLineId = amLineId;
    }

    public String getAmortisationId() {
        return amortisationId;
    }

    public void setAmortisationId(String amortisationId) {
        this.amortisationId = amortisationId;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String amortisationId;
    public BigDecimal payment;
    public BigDecimal principal;
    public BigDecimal interest;
    public BigDecimal balance;

}
