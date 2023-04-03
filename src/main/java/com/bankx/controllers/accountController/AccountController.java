package com.bankx.controllers.accountController;

import com.bankx.models.account.DepositAmount;
import com.bankx.models.account.CreditTransferRequest;
import com.bankx.models.account.WithdrawAmount;
import com.bankx.services.accountService.AccountService;
import com.bankx.utility.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/checkAmount/{customerId}")
    public ResponseEntity<Object> checkAmount(@PathVariable int customerId){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.checkAmount(customerId));
    }

    @PutMapping("/depositAmount")
    public ResponseEntity<Object> depositAmount(@RequestBody DepositAmount depositAmount){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.depositAmount(depositAmount));
    }

    @PutMapping("/withdrawAmount")
    public ResponseEntity<Object> withdrawAmountFromCurrentAccount(@RequestBody WithdrawAmount withdrawAmount){
        return ResponseHandler.generateResponse(HttpStatus.OK ,true , accountService.withdrawAmount(withdrawAmount));
    }


    @PutMapping("/transferAmountSavingsToCurrent")
    public ResponseEntity<Object> transferAmountSavingsToCurrentAccount(@RequestBody CreditTransferRequest transferAmount){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.transferAmountSavingsToCurrentAccount(transferAmount));
    }

    @PutMapping("/transferAmountBwUsers")
    public ResponseEntity<Object> transferAmountBWUsers(@RequestBody CreditTransferRequest transferAmount){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.transferAmountBWUsers(transferAmount));
    }

}
