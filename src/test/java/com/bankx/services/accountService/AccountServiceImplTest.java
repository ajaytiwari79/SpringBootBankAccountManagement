package com.bankx.services.accountService;

import com.bankx.repositories.accountRepositry.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

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

}