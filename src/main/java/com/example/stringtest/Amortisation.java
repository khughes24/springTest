package com.example.stringtest;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Amortisation {

    public String amortisationId;
    public String userId;
    public BigDecimal principal;
    public BigDecimal debt;
    public BigDecimal interest;

    public ArrayList<AmortisationDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<AmortisationDetail> details) {
        this.details = details;
    }

    public ArrayList<AmortisationDetail> details;

    public String getAmortisationId() {
        return amortisationId;
    }

    public void setAmortisationId(String amortisationId) {
        this.amortisationId = amortisationId;
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

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }
}
