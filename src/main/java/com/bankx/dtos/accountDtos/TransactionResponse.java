package com.bankx.dtos.accountDtos;

import com.bankx.models.transactions.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private List<Transaction> transactionList;
    private String message;
}
