package com.bankx.services.accountService;

import com.bankx.models.account.Account;
import com.bankx.models.account.AccountType;

public interface AccountBalanceService {

    boolean debitBalanceFromAccount(Account account, double debitBalance);

    boolean creditBalanceToAccount(Account account, double balance);

    double getTwoPlaceDecimalValue(double amount);
}
