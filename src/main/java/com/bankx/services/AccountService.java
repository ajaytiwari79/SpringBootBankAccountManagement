package com.bankx.services;

import com.bankx.entites.Customer;
import com.bankx.models.AccountBalance;
import com.bankx.models.CreditTransferRequest;
import com.bankx.models.DepositAmountRequest;
import com.bankx.models.WithdrawAmountRequest;
import com.bankx.entites.Transaction;

import java.util.List;

public interface AccountService {
    List<AccountBalance> checkAmount(int customerId);

    Transaction depositAmount(DepositAmountRequest depositAmount);

    List<AccountBalance> interTransfer(CreditTransferRequest transferAmount);

    boolean creditTransfer(CreditTransferRequest transferAmount);

    Transaction withdrawAmount(WithdrawAmountRequest withdrawAmount);

    void sendNotification(CreditTransferRequest transferAmount, Customer fromUser, Customer toUser);
}
