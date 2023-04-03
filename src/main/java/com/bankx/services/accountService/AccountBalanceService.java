package com.bankx.services.accountService;

import com.bankx.entites.account.Account;

public interface AccountBalanceService {

    boolean debitAmountFromAccount(Account account, double debitBalance);

    boolean creditAmountToAccount(Account account, double balance);

    double getTwoPlaceDecimalValue(double amount);
}
