package com.bankx.services.transactionService;

import com.bankx.models.transactions.Transaction;
import com.bankx.repositories.transactionsRepository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Async
    public void createTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public Object matchAllTransactionsByBankZ(List<Transaction> transactions, String bankz){
        Date startDate = new Date(String.valueOf(LocalDate.now()));
        Date endDate = new Date(String.valueOf(LocalDate.now().plusDays(1)));
        List<Transaction> transactionList = transactionRepository.getAllTransactionByBANKZToday(bankz,startDate,endDate);
        //how we can match transaction data
        return null;
    }
}
