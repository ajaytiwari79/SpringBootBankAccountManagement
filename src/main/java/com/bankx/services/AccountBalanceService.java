package com.bankx.services;

import com.bankx.entites.Account;

public interface AccountBalanceService {

    boolean debitAmountFromAccount(Account account, double debitBalance);

    boolean creditAmountToAccount(Account account, double balance);

    double getTwoPlaceDecimalValue(double amount);
}
