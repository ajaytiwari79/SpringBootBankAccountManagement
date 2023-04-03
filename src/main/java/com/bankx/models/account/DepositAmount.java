package com.bankx.models.account;

import com.bankx.entites.account.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositAmount {
    private double amount;
    private int customerId;
    private AccountType accountType;
    private String bankName;
}
