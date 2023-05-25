package com.bankx.services;

import com.bankx.entites.Account;
import com.bankx.enums.AccountType;
import com.bankx.entites.Customer;
import com.bankx.entites.Transaction;
import com.bankx.enums.TransactionType;
import com.bankx.models.AccountBalance;
import com.bankx.models.CreditTransferRequest;
import com.bankx.models.DepositAmountRequest;
import com.bankx.models.WithdrawAmountRequest;
import com.bankx.repositories.AccountRepository;
import com.bankx.repositories.NotificationRepository;
import com.bankx.repositories.TransactionRepository;
import com.bankx.services.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountBalanceService accountBalanceService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Value("${interestRate}")
    private double interestRate;

    @Autowired
    private AccountService accountService;

    @Mock
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setAccountRepository(){
        this.accountService = new AccountServiceImpl(this.accountRepository, this.accountBalanceService,this.transactionService,this.transactionRepository,this.notificationRepository);
    }


    @Test
    void TestSavingAccount(){
        DepositAmountRequest depositAmount = new DepositAmountRequest(1, 100, AccountType.SAVINGS, "BankX");
        Customer customer = Customer.builder().username("test").dob(new Date()).state("UP").city("Muzaffarnagar").email("abhi@gmail.com").build();
        customer.setId(1);
        Account savingAccount = Account.builder().accountType(AccountType.SAVINGS).amount(depositAmount.getAmount()).accountStatus(true).customer(customer) .build();
        Transaction t = Transaction.builder().amount(savingAccount.getAmount()).fromUser(savingAccount.getCustomer()).creditSuccess(true).bankName(depositAmount.getBankName()).transactionType(TransactionType.CREDIT).build();
        when(accountRepository.getSavingsAccountByUser(eq(depositAmount.getCustomerId()), eq(true), eq(depositAmount.getAccountType()))).thenReturn(savingAccount);
        when(accountBalanceService.creditAmountToAccount(savingAccount, depositAmount.getAmount())).thenReturn(true);
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(t);
        Transaction transaction = accountService.depositAmount(depositAmount);
        assertNotNull(transaction);
    }
    @Test
    void testWithdrawAmount() {
        WithdrawAmountRequest withdrawAmount = new WithdrawAmountRequest();
        withdrawAmount.setCustomerId(1);
        withdrawAmount.setAccountType(AccountType.SAVINGS);
        withdrawAmount.setAmount(10);
        withdrawAmount.setBankName("bankX");
        Customer customer = Customer.builder().username("test").dob(new Date()).state("UP").city("Muzaffarnagar").email("abhi@gmail.com").build();
        customer.setId(1);
        Account savingAccount = Account.builder().accountType(AccountType.SAVINGS).amount(100.0).accountStatus(true).customer(customer) .build();
        Transaction transaction = Transaction.builder().amount(withdrawAmount.getAmount()).fromUser(savingAccount.getCustomer()).creditSuccess(true).bankName(withdrawAmount.getBankName()).transactionType(TransactionType.DEBIT).build();
        when(accountRepository.getSavingsAccountByUser(1, true, AccountType.SAVINGS)).thenReturn(savingAccount);
        when(accountBalanceService.debitAmountFromAccount(savingAccount, withdrawAmount.getAmount())).thenReturn(true);
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);
        Transaction result = accountService.withdrawAmount(withdrawAmount);
        assertNotNull(result);
        assertTrue(result.isCreditSuccess());
    }


    @Test
    public void transferAmountSavingsToCurrentAccountTest(){
        CreditTransferRequest transferAmount = new CreditTransferRequest(1,10,1,"bankX");
        Customer customer = Customer.builder().username("test").dob(new Date()).state("UP").city("Muzaffarnagar").email("abhi@gmail.com").build();
        customer.setId(1);
        Account savingAccount = Account.builder().accountType(AccountType.SAVINGS).amount(100.0).accountStatus(true).customer(customer) .build();
        Account currentAccount = Account.builder().accountType(AccountType.CURRENT).amount(100.0).accountStatus(true).customer(customer) .build();
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(savingAccount);
        accounts.add(currentAccount);
        List<AccountBalance> accountBalanceList = new ArrayList<>();
        accountBalanceList.add( new AccountBalance(savingAccount.getAccountType() , savingAccount.getAmount()));
        accountBalanceList.add( new AccountBalance(currentAccount.getAccountType() , currentAccount.getAmount()));
        when(this.accountRepository.getAccountsByCustomerId(transferAmount.getCreditorId(),true)).thenReturn(accounts);
        when(this.accountBalanceService.debitAmountFromAccount(savingAccount , transferAmount.getAmount())).thenReturn(true);
        when(this.accountBalanceService.creditAmountToAccount(currentAccount, transferAmount.getAmount())).thenReturn(true);
        List<AccountBalance> list = this.accountService.interTransfer(transferAmount);
        assertEquals(2, list.size());
    }

    @Test
    public void testTransferAmountBWUsers() {
        Customer customer = Customer.builder().username("test").dob(new Date()).state("UP").city("Muzaffarnagar").email("abhi@gmail.com").build();
        customer.setId(1);
        Account debtorAccount1 = Account.builder().accountType(AccountType.CURRENT).amount(100.0).accountStatus(true).customer(customer) .build();
        debtorAccount1.setId(1);
        Account debtorAccount2 = Account.builder().accountType(AccountType.SAVINGS).amount(100.0).accountStatus(true).customer(customer) .build();
        debtorAccount2.setId(2);
        Customer creditorCustomer =  Customer.builder().username("test1").dob(new Date()).state("UP").city("Muzaffarnagar").email("abhijeet@gmail.com").build();
        creditorCustomer.setId(2);
        Account creditorAccount1 = Account.builder().accountType(AccountType.CURRENT).amount(100.0).accountStatus(true).customer(creditorCustomer) .build();
        creditorAccount1.setId(3);
        Account creditorAccount2 = Account.builder().accountType(AccountType.SAVINGS).amount(100.0).accountStatus(true).customer(creditorCustomer) .build();
        creditorAccount2.setId(4);
        CreditTransferRequest transferAmount = new CreditTransferRequest(1, 10.0, 2, "BankX");
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(debtorAccount1);
        accounts.add(debtorAccount2);
        accounts.add(creditorAccount1);
        accounts.add(creditorAccount2);
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        when(accountRepository.getAccountByIds(anyList(), eq(true))).thenReturn(accounts);
        when(accountBalanceService.debitAmountFromAccount(debtorAccount1, transferAmount.getAmount())).thenReturn(true);
        when(accountBalanceService.creditAmountToAccount(creditorAccount2, transferAmount.getAmount())).thenReturn(true);
        boolean result = accountService.creditTransfer(transferAmount);
        assertTrue(result);
    }


}