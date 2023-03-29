package com.bankx.controllers.accountController;

import com.bankx.dtos.accountDtos.CreditBalance;
import com.bankx.dtos.accountDtos.TransferAmount;
import com.bankx.services.accountService.AccountService;
import com.bankx.services.accountService.AccountServiceImpl;
import com.bankx.utility.Constants;
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

    @GetMapping("/get_balance/{c_id}")
    public ResponseEntity<Object> getBalance(@PathVariable int c_id){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.checkBalance(c_id));
    }

    @PutMapping("/add_balance_to_savings_account")
    public ResponseEntity<Object> addBalanceToSavingsAccount(@RequestBody CreditBalance creditBalance){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.addBalanceToSavingsAccount(creditBalance, Constants.BANKX));
    }

    @PutMapping("/transfer_balance_savings_to_current_account")
    public ResponseEntity<Object> transferBalanceSavingsToCurrentAccount(@RequestBody CreditBalance creditBalance){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.transferBalanceSavingsToCurrentAccount(creditBalance , Constants.BANKX));
    }

    @PutMapping("/transfer_amount_bw_users")
    public ResponseEntity<Object> transferAmountBWUsers(@RequestBody TransferAmount transferAmount){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.transferAmountBWUsers(transferAmount, Constants.BANKX));
    }

}
