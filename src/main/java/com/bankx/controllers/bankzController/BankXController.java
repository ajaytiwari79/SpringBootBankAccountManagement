package com.bankx.controllers.bankzController;

import com.bankx.entites.transactions.Transaction;
import com.bankx.services.accountService.AccountService;
import com.bankx.services.transactionService.TransactionService;
import com.bankx.utility.Constants;
import com.bankx.utility.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankXController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @PutMapping("/reconciliation")
    public ResponseEntity<Object> matchAllTransactionsByBankZ(@RequestBody List<Transaction> transactions ){
        return ResponseHandler.generateResponse(HttpStatus.OK ,true , transactionService.matchAllTransactionsByBankZ(transactions , Constants.BANKZ));
    }


}
