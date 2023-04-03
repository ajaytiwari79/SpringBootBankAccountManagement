package com.bankx.services.accountService;

import com.bankx.entites.customer.Customer;
import com.bankx.models.account.AccountBalance;
import com.bankx.models.account.CreditTransferRequest;
import com.bankx.models.account.DepositAmount;
import com.bankx.models.account.WithdrawAmount;
import com.bankx.entites.transactions.Transaction;

import java.util.List;

public interface AccountService {
    List<AccountBalance> checkAmount(int customerId);

    Transaction depositAmount(DepositAmount depositAmount);

    List<AccountBalance> transferAmountSavingsToCurrentAccount(CreditTransferRequest transferAmount);

    boolean transferAmountBWUsers(CreditTransferRequest transferAmount);

    Transaction withdrawAmount(WithdrawAmount withdrawAmount);

    void sendNotification(CreditTransferRequest transferAmount, Customer fromUser, Customer toUser);
}
