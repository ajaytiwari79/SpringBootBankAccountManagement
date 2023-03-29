package com.bankx.services.accountService;

import com.bankx.dtos.accountDtos.AccountBalance;
import com.bankx.dtos.accountDtos.CreditBalance;
import com.bankx.dtos.accountDtos.TransferAmount;
import com.bankx.models.account.Account;
import com.bankx.models.account.AccountType;
import com.bankx.models.customer.Customer;
import com.bankx.models.exception.NotValidException;
import com.bankx.models.notification.Notification;
import com.bankx.models.transactions.Transaction;
import com.bankx.repositories.accountRepositry.AccountRepository;
import com.bankx.repositories.notificationRepository.NotificationRepository;
import com.bankx.repositories.transactionsRepository.TransactionRepository;
import com.bankx.services.transactionService.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private static final Logger LOGGER = LogManager.getLogger(String.valueOf(AccountServiceImpl.class));

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountBalanceService accountBalanceService;

    @Value("${interestRate}")
    private double interestRate;

    public AccountServiceImpl(AccountRepository accountRepository,AccountBalanceService accountBalanceService) {
        this.accountRepository = accountRepository;
        this.accountBalanceService = accountBalanceService;
    }

    public List<AccountBalance> checkBalance(int c_id) {
        List<AccountBalance> checkBalanceList =  accountRepository.getAccountBalance(c_id,true);
        return checkBalanceList;
    }

    public double addBalanceToSavingsAccount(CreditBalance creditBalance, String bankName) {
        Account account = accountRepository.getSavingsAccountBalanceByUser(creditBalance.getC_id(),true , AccountType.SAVINGS);
        double currentBalance = account.getBalance()+creditBalance.getBalance();
        double interest = (currentBalance * interestRate)/100;
        boolean isBalanceCredit = accountBalanceService.creditBalanceToAccount(account, (creditBalance.getBalance() + interestRate));
        if(isBalanceCredit) {
            transactionService.createTransaction(Transaction.builder().toUser(account.getCustomer()).creditSuccess(true).bankName(bankName).build());
        }
        return currentBalance+interest;
    }

    public boolean getBalance(CreditBalance creditBalance, String bankz){
        Account account = accountRepository.getSavingsAccountBalanceByUser(creditBalance.getC_id(),true , AccountType.CURRENT);
        double balance = creditBalance.getBalance() + (creditBalance.getBalance()*interestRate) * .001;
        return accountBalanceService.debitBalanceFromAccount(account , balance );
    }

    @Transactional
    public List<AccountBalance> transferBalanceSavingsToCurrentAccount(CreditBalance creditBalance , String bankName) {
        List<Account> accounts =  accountRepository.getAccountsByCustomerId(creditBalance.getC_id(),true);
        List<AccountBalance> accountBalanceList = new ArrayList<>();
        boolean isBalanceDeduct = false , isBalanceCredit = false;
        Transaction transaction=null;
        for(Account account : accounts){
            AccountBalance accountBalance =  new AccountBalance();
            accountBalance.setAccountType(account.getAccountType());
            if(account.getAccountType().equals(AccountType.SAVINGS)){
                isBalanceDeduct = accountBalanceService.debitBalanceFromAccount(account , creditBalance.getBalance());
                accountBalance.setBalance(account.getBalance());
                transaction=Transaction.builder().fromUser(account.getCustomer()).debitSuccess(isBalanceDeduct).bankName(bankName).build();
            }else{
                if(isBalanceDeduct) {
                    isBalanceCredit  = accountBalanceService.creditBalanceToAccount(account, creditBalance.getBalance());
                    accountBalance.setBalance(account.getBalance());
                    transaction.setToUser(account.getCustomer());
                    transaction.setCreditSuccess(isBalanceCredit);
                }
            }
            transactionService.createTransaction(transaction);
            accountBalanceList.add(accountBalance);
        }
        return accountBalanceList;
    }

    @Transactional
    public boolean transferAmountBWUsers(TransferAmount transferAmount, String bankName) {
        List<Integer> ids = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();
        ids.add(transferAmount.getCreditor_id());
        ids.add(transferAmount.getDebtor_id());
        List<Account> accounts =  accountRepository.getAccountByIds(ids , true);
        Optional<Account> debtorsAccount = accounts.stream().filter(e-> e.getCustomer().getId() == transferAmount.getDebtor_id() && e.getAccountType().equals(AccountType.CURRENT)).findFirst();
        Optional<Account> creditorsAccount = accounts.stream().filter(e-> e.getCustomer().getId() == transferAmount.getCreditor_id() && e.getAccountType().equals(AccountType.SAVINGS)).findFirst();
        Account  debtorAccount=null , creditorAccount= null;
        Transaction transaction= null;
        if(debtorsAccount.isPresent()){
            debtorAccount = debtorsAccount.get();
            transaction = Transaction.builder().fromUser(debtorAccount.getCustomer()).bankName(bankName).build();
            if(debtorAccount.getBalance() >= (transferAmount.getAmount() + ((transferAmount.getAmount() * interestRate)/100))){
                boolean isBalanceDeduct = accountBalanceService.debitBalanceFromAccount(debtorAccount , transferAmount.getAmount() + ((transferAmount.getAmount() * interestRate)/100));
                transaction.setDebitSuccess(true);
                transaction.setEnables(true);
            }
            else{
                transaction.setDebitSuccess(false);
                transaction.setEnables(true);
                transactionService.createTransaction(transaction);
                throw new NotValidException("Current account does not have enough balance");
            }
        }else{
            throw  new NotValidException("Debtors Account Not Fount");
        }
        if(creditorsAccount.isPresent()){
            creditorAccount = creditorsAccount.get();
            transaction.setCreditSuccess(true);
            transaction.setToUser(creditorAccount.getCustomer());
            accountBalanceService.creditBalanceToAccount(creditorAccount , transferAmount.getAmount() + ((creditorAccount.getBalance()+transferAmount.getAmount())*interestRate)/100);
        }else{
            throw new NotValidException("Creditors Account not found");
        }
        transactionRepository.save(transaction);
        sendNotification(transferAmount,debtorAccount.getCustomer() , creditorAccount.getCustomer());
        return true;
    }

    private void sendNotification(TransferAmount transferAmount, Customer fromUser, Customer toUser) {
        ArrayList<Notification> notifications = new ArrayList<>();
        Notification debtorNotification = new Notification(),creditorNotification = new Notification();
        debtorNotification.setCustomer(fromUser);
        debtorNotification.setAmount(transferAmount.getAmount());
        notifications.add(debtorNotification);
        creditorNotification.setCustomer(toUser);
        debtorNotification.setAmount(transferAmount.getAmount());
        notifications.add(creditorNotification);
        notificationRepository.saveAll(notifications);
    }
}
