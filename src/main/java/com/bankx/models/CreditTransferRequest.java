package com.bankx.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditTransferRequest {
    private int debtorId;
    private double amount;
    private int creditorId;
    private String bankName;
}
