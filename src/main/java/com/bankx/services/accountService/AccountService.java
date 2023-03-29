package com.bankx.services.accountService;

import com.bankx.dtos.accountDtos.AccountBalance;
import com.bankx.dtos.accountDtos.CreditBalance;
import com.bankx.dtos.accountDtos.TransferAmount;
import com.bankx.models.account.Account;
import com.bankx.models.customer.Customer;

import java.util.List;

public interface AccountService {
    List<AccountBalance> checkBalance(int c_id);

    double addBalanceToSavingsAccount(CreditBalance creditBalance,String bankName);

    List<AccountBalance> transferBalanceSavingsToCurrentAccount(CreditBalance creditBalance, String bankName);

    boolean transferAmountBWUsers(TransferAmount transferAmount,String bankName);

    boolean getBalance(CreditBalance creditBalance, String bankz);
}
