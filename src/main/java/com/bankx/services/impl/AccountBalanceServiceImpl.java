package com.bankx.services.impl;

import com.bankx.entites.Account;
import com.bankx.repositories.AccountRepository;
import com.bankx.services.AccountBalanceService;
import com.bankx.utilities.NotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AccountBalanceServiceImpl implements AccountBalanceService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountBalanceServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean debitAmountFromAccount(Account account , double amount){
        boolean isBalanceDebit = false;
        if(account.getAmount() >= getTwoPlaceDecimalValue(amount)) {
            account.setAmount(getTwoPlaceDecimalValue(account.getAmount() - amount));
        }else{
            throw new NotValidException("Current Account does not have enough balance to transfer");
        }
        Account account1 = accountRepository.save(account);
        if(account1 !=null){
            isBalanceDebit = true;
        }
        return isBalanceDebit;
    }

    public boolean creditAmountToAccount(Account account, double amount){
        boolean isBalanceCredit = false;
        amount = getTwoPlaceDecimalValue(account.getAmount() + amount);
        account.setAmount(amount);
        Account updatedAccount = accountRepository.save(account);
        if(updatedAccount.getAmount() == amount){
            isBalanceCredit=true;
        }
        return isBalanceCredit;
    }

    public double getTwoPlaceDecimalValue(double amount){
        BigDecimal bd = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
