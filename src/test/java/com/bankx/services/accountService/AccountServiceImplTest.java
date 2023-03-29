package com.bankx.services.accountService;

import com.bankx.dtos.accountDtos.AccountBalance;
import com.bankx.dtos.accountDtos.CreditBalance;
import com.bankx.models.account.Account;
import com.bankx.models.account.AccountType;
import com.bankx.models.customer.Customer;
import com.bankx.repositories.accountRepositry.AccountRepository;
import com.bankx.services.customerService.CustomerServiceImpl;
import com.bankx.utility.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountBalanceService accountBalanceService;

    @Value("${interestRate}")
    private double interestRate;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setAccountRepository(){
        this.accountService = new AccountServiceImpl(this.accountRepository, this.accountBalanceService);
    }

    @Test
    public void getBalance(){
        Account account = new Account(AccountType.SAVINGS , 500 ,true,new Customer(1,false ,"abhijeet","abhijeet@gmail.com",new Date(),"1234567890","abc","xyz","121345678","UP","251306"));
        when(accountRepository.getSavingsAccountBalanceByUser(1 , true,AccountType.CURRENT)).thenReturn(account);
        CreditBalance creditBalance = new CreditBalance(200, 1);
        ReflectionTestUtils.setField(accountService, "interestRate", 2);
        when(accountBalanceService.debitBalanceFromAccount(account , creditBalance.getBalance() + (creditBalance.getBalance()*interestRate) * .001)).thenReturn(true);
        assertEquals(true,accountService.getBalance(creditBalance, Constants.BANKX));
    }

    @Test
    public void addBalanceToSavingsAccount(){
        Account account = new Account(AccountType.SAVINGS , 500 ,true,new Customer(1,false ,"abhijeet","abhijeet@gmail.com",new Date(),"1234567890","abc","xyz","121345678","UP","251306"));
        when(accountRepository.getSavingsAccountBalanceByUser(1,true,AccountType.SAVINGS)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        ReflectionTestUtils.setField(accountService, "interestRate", 2);
        CreditBalance creditBalance = new CreditBalance(200, 1);
        when(accountBalanceService.creditBalanceToAccount(account, 200)).thenReturn(true);
        assertEquals(714 , accountService.addBalanceToSavingsAccount(creditBalance , Constants.BANKX));
    }

}