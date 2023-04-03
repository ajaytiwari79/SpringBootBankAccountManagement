package com.bankx.api;

import com.bankx.models.DepositAmountRequest;
import com.bankx.models.CreditTransferRequest;
import com.bankx.models.WithdrawAmountRequest;
import com.bankx.services.AccountService;
import com.bankx.utilities.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountApiController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/checkAmount/{customerId}")
    public ResponseEntity<Object> checkAmount(@PathVariable int customerId){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.checkAmount(customerId));
    }

    @PutMapping("/depositAmount")
    public ResponseEntity<Object> depositAmount(@RequestBody DepositAmountRequest depositAmountRequest){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.depositAmount(depositAmountRequest));
    }

    @PutMapping("/withdrawAmount")
    public ResponseEntity<Object> withdrawAmount(@RequestBody WithdrawAmountRequest withdrawAmountRequest){
        return ResponseHandler.generateResponse(HttpStatus.OK ,true , accountService.withdrawAmount(withdrawAmountRequest));
    }


    @PutMapping("/intertransfer")
    public ResponseEntity<Object> interTransfer(@RequestBody CreditTransferRequest creditTransferRequest){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.interTransfer(creditTransferRequest));
    }

    @PutMapping("/creditTransfer")
    public ResponseEntity<Object> creditTransfer(@RequestBody CreditTransferRequest creditTransferRequest){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , accountService.creditTransfer(creditTransferRequest));
    }

}
