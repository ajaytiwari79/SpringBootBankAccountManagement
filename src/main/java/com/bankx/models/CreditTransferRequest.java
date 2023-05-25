package com.bankx.models;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditTransferRequest {
    private int debtorId;
    private double amount;
    private int creditorId;
    private String bankName;
}
