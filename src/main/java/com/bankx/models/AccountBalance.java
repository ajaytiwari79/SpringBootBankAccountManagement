package com.bankx.models;

import com.bankx.enums.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AccountBalance {
    private AccountType accountType;
    private double amount;

    public AccountBalance(AccountType accountType, double amount) {
        this.accountType = accountType;
        this.amount = amount;
    }

}
