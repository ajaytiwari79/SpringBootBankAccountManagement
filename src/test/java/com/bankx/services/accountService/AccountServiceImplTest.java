package com.bankx.services.accountService;

import com.bankx.entites.account.Account;
import com.bankx.entites.account.AccountType;
import com.bankx.entites.customer.Customer;
import com.bankx.entites.transactions.Transaction;
import com.bankx.entites.transactions.TransactionType;
import com.bankx.models.account.AccountBalance;
import com.bankx.models.account.CreditTransferRequest;
import com.bankx.models.account.DepositAmount;
import com.bankx.models.account.WithdrawAmount;
import com.bankx.repositories.accountRepositry.AccountRepository;
import com.bankx.repositories.notificationRepository.NotificationRepository;
import com.bankx.repositories.transactionsRepository.TransactionRepository;
import com.bankx.services.transactionService.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
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

    @Mock
    private AccountService accountService;

    @Mock
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setAccountRepository(){
        this.accountService = new AccountServiceImpl(this.accountRepository, this.accountBalanceService,this.transactionService,this.transactionRepository,this.notificationRepository);
    }


    @Test
    void savingAccount(){
        DepositAmount depositAmount = new DepositAmount(1, 100, AccountType.SAVINGS, "BankX");
        Customer customer = Customer.builder().username("test").dob(new Date()).state("UP").city("Muzaffarnagar").email("abhi@gmail.com").build();
        customer.setId(1);
        Account savingAccount = Account.builder().accountType(AccountType.SAVINGS).amount(depositAmount.getAmount()).accountStatus(true).customer(customer) .build();
        Transaction t = Transaction.builder().amount(savingAccount.getAmount()).fromUser(savingAccount.getCustomer()).creditSuccess(true).bankName(depositAmount.getBankName()).transactionType(TransactionType.CREDIT).build();
        when(accountRepository.getSavingsAccountByUser(eq(depositAmount.getCustomerId()), eq(true), eq(depositAmount.getAccountType()))).thenReturn(savingAccount);
        when(accountBalanceService.creditAmountToAccount(savingAccount, depositAmount.getAmount())).thenReturn(true);
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(t);
        Transaction transaction = accountService.depositAmount(depositAmount);
        assertNotNull(transaction);
        assertEquals(1, transaction.getAmount());
        assertTrue(transaction.isCreditSuccess());
        assertEquals("BankX", transaction.getBankName());
        assertEquals(TransactionType.CREDIT, transaction.getTransactionType());

    }
    @Test
    void testWithdrawAmount() {
        WithdrawAmount withdrawAmount = new WithdrawAmount();
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
        assertEquals(withdrawAmount.getAmount(), result.getAmount());
        assertTrue(result.isCreditSuccess());
        assertEquals(withdrawAmount.getBankName(), result.getBankName());
        assertEquals(TransactionType.DEBIT, result.getTransactionType());
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
        List<AccountBalance> list = this.accountService.transferAmountSavingsToCurrentAccount(transferAmount);
        assertEquals(2, list.size());
        assertEquals(savingAccount.getAmount() ,list.get(0).getAmount());
        assertEquals(currentAccount.getAmount() , list.get(1).getAmount());
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
        doNothing().when(transactionRepository.save(any(Transaction.class)));
        doNothing().when( notificationRepository.saveAll(any(ArrayList.class)));
        accountService.sendNotification(transferAmount , any(Customer.class) , any(Customer.class));
        boolean result = accountService.transferAmountBWUsers(transferAmount);
        assertTrue(result);
    }


}