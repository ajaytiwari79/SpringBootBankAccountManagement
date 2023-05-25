package com.bankx.models;

import com.bankx.enums.AccountType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawAmountRequest {
    private double amount;
    private int customerId;
    private AccountType accountType;
    private String bankName;
}
