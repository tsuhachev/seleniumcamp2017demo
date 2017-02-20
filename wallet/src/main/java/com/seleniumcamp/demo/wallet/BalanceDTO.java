package com.seleniumcamp.demo.wallet;

import java.util.Currency;

/**
 * This class is a balance dto
 */
public class BalanceDTO {

    private Double balance;

    private Currency currency;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
