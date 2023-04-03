package com.bankx.models;

import com.bankx.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositAmountRequest {
    private double amount;
    private int customerId;
    private AccountType accountType;
    private String bankName;
}
