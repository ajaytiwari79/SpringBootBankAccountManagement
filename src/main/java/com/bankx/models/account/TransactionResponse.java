package com.bankx.models.account;

import com.bankx.entites.transactions.Transaction;
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
