package com.bankx.dtos.accountDtos;

import com.bankx.models.account.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AccountBalance {
    private AccountType accountType;
    private double balance;

    public AccountBalance(AccountType accountType, double balance) {
        this.accountType = accountType;
        this.balance = balance;
    }
}
