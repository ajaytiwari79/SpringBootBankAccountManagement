package com.bankx.dtos.transactionsDtos;

import com.bankx.models.transactions.TransactionType;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private TransactionType transactionType;
    private boolean debitSuccess;
    private boolean creditSuccess;
    private double amount;
    private boolean enables;
    private String fromUser;
    private String toUser;
}
