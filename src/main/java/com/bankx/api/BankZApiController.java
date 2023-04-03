package com.bankx.api;

import com.bankx.entites.Transaction;
import com.bankx.services.AccountService;
import com.bankx.services.TransactionService;
import com.bankx.utilities.Constants;
import com.bankx.utilities.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bankz")
public class BankZApiController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @PutMapping("/reconcileTransactions")
    public ResponseEntity<Object> reconcileTransactions(@RequestBody List<Transaction> transactions ){
        return ResponseHandler.generateResponse(HttpStatus.OK ,true , transactionService.reconcileTransactions(transactions , Constants.BANKZ));
    }


}
