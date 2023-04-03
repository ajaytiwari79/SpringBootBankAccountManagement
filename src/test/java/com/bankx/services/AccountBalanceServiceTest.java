package com.bankx.services;

import com.bankx.entites.Account;
import com.bankx.enums.AccountType;
import com.bankx.entites.Customer;
import com.bankx.repositories.AccountRepository;
import com.bankx.services.impl.AccountBalanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AccountBalanceServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Value("${interestRate}")
    private double interestRate;
    private AccountBalanceServiceImpl accountService;

    @BeforeEach
    public void setAccountRepository(){
        this.accountService = new AccountBalanceServiceImpl(this.accountRepository);
    }

    @Test
    public void debitBalanceFromAccount(){
        Account account = Account.builder().accountType(AccountType.CURRENT).amount(100.0).accountStatus(true).customer(Customer.builder().dob(new Date()).state("UP").city("Muzaffarnagar").email("abhi@gmail.com").build()) .build();
        when(this.accountRepository.save(account)).thenReturn(account);
        assertTrue(this.accountService.debitAmountFromAccount(account,10));
    }

    @Test
    public void creditAmountToAccount(){
        Account account = Account.builder().accountType(AccountType.CURRENT).amount(100.0).accountStatus(true).customer(Customer.builder().dob(new Date()).state("UP").city("Muzaffarnagar").email("abhi@gmail.com").build()) .build();
        when(this.accountRepository.save(account)).thenReturn(account);
        assertTrue(this.accountService.creditAmountToAccount(account , 100));
    }

}