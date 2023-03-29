package com.bankx.dtos.accountDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferAmount {
    private int debtor_id;
    private double amount;
    private int creditor_id;
}
