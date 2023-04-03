package com.bankx.services;

import com.bankx.entites.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction build);

    Object reconcileTransactions(List<Transaction> transactions, String bankz);
}
