package com.example.stringtest;

import java.math.BigDecimal;

public class Loan {
    public String loanId;
    public String userId;
    public BigDecimal principal;
    public Integer durration;
    public BigDecimal interest;
    public BigDecimal remaining;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public Integer getDurration() {
        return durration;
    }

    public void setDurration(Integer durration) {
        this.durration = durration;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getRemaining() {
        return remaining;
    }

    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }
}
