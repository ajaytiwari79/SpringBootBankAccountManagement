package com.bankx.services.accountService;

import com.bankx.models.account.Account;
import com.bankx.models.account.AccountType;
import com.bankx.models.exception.NotValidException;
import com.bankx.repositories.accountRepositry.AccountRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class AccountBalanceServiceImpl implements AccountBalanceService{

    @Autowired
    private AccountRepository accountRepository;

    public boolean debitBalanceFromAccount(Account account , double balance){
        boolean isBalanceDebit = false;
        if(account.getBalance() >= getTwoPlaceDecimalValue(balance)) {
            account.setBalance(getTwoPlaceDecimalValue(account.getBalance() - balance));
            isBalanceDebit = true;
        }else{
            throw new NotValidException("Savings Account does not have enough balance to transfer");
        }
        accountRepository.save(account);
        return isBalanceDebit;
    }

    public boolean creditBalanceToAccount(Account account, double balance){
        boolean isBalanceCredit = false;
        double amount = getTwoPlaceDecimalValue(account.getBalance() + balance);
        account.setBalance(amount);
        Account updatedAccount = accountRepository.save(account);
        if(updatedAccount.getBalance() == amount){
            isBalanceCredit=true;
        }
        return isBalanceCredit;
    }

    public double getTwoPlaceDecimalValue(double amount){
        BigDecimal bd = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
