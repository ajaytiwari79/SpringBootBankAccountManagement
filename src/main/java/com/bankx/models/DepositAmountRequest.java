package com.bankx.models;

import com.bankx.enums.AccountType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositAmountRequest {
    private double amount;
    private int customerId;
    private AccountType accountType;
    private String bankName;
}
