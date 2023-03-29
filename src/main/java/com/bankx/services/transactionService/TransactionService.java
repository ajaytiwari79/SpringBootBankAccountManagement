package com.bankx.services.transactionService;

import com.bankx.models.transactions.Transaction;

import java.util.List;

public interface TransactionService {

    void createTransaction(Transaction build);

    Object matchAllTransactionsByBankZ(List<Transaction> transactions, String bankz);
}
