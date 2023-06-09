package com.bankx.services.impl;

import com.bankx.entites.Transaction;
import com.bankx.models.TransactionResponse;
import com.bankx.repositories.TransactionRepository;
import com.bankx.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public TransactionResponse reconcileTransactions(List<Transaction> transactions, String bankz){
        boolean isTransactionMatched = false;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        List<Transaction> transactionList = transactionRepository.getAllTransactionByBANKZToday(bankz,startDate,endDate);
        List<Transaction> notMatchedTransactions = new ArrayList<>();
        for(Transaction transaction : transactions){
            boolean isTransactionAvailable = false;
            for(Transaction transaction1 : transactionList){
               if(transaction1.getId()==transaction.getId()){
                   isTransactionAvailable = true;
                   if(transaction1.getAmount()!=transaction.getAmount() || !transaction1.getTransactionType().equals(transaction.getTransactionType())){
                       transaction.setFromUser(null);
                       transaction.setToUser(null);
                       notMatchedTransactions.add(transaction);
                   }
               }
            }
            if(!isTransactionAvailable){
                transaction.setFromUser(null);
                transaction.setToUser(null);
                notMatchedTransactions.add(transaction);
            }
        }
        TransactionResponse transactionResponse = new TransactionResponse();
        if(notMatchedTransactions.size() > 0){
            transactionResponse.setTransactionList(notMatchedTransactions);
            transactionResponse.setMessage("Not matched transactions");
        }else{
            transactionResponse.setMessage("All transactions matched success");
        }
        return transactionResponse;
    }
}
