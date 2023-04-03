package com.bankx.services.accountService;

import com.bankx.models.account.AccountBalance;
import com.bankx.models.account.CreditTransferRequest;
import com.bankx.models.account.DepositAmount;
import com.bankx.models.account.WithdrawAmount;
import com.bankx.entites.account.Account;
import com.bankx.entites.account.AccountType;
import com.bankx.entites.customer.Customer;
import com.bankx.entites.exception.NotValidException;
import com.bankx.entites.notification.Notification;
import com.bankx.entites.transactions.Transaction;
import com.bankx.entites.transactions.TransactionType;
import com.bankx.repositories.accountRepositry.AccountRepository;
import com.bankx.repositories.notificationRepository.NotificationRepository;
import com.bankx.repositories.transactionsRepository.TransactionRepository;
import com.bankx.services.transactionService.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
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

    public AccountServiceImpl(AccountRepository accountRepository, AccountBalanceService accountBalanceService, TransactionService transactionService, TransactionRepository transactionRepository, NotificationRepository notificationRepository) {
        this.accountRepository = accountRepository;
        this.accountBalanceService = accountBalanceService;
        this.transactionService=transactionService;
        this.transactionRepository=transactionRepository;
        this.notificationRepository=notificationRepository;
    }

    public List<AccountBalance> checkAmount(int customerId) {
        List<AccountBalance> checkBalanceList = accountRepository.getAccountBalance(customerId, true);
        return checkBalanceList;
    }

    @Transactional
    public Transaction depositAmount(DepositAmount depositAmount) {
        Account account = accountRepository.getSavingsAccountByUser(depositAmount.getCustomerId(), true, depositAmount.getAccountType());
        double currentAmount = account.getAmount() + depositAmount.getAmount();
        double interest = (currentAmount * interestRate) / 100;
        boolean isBalanceCredit = accountBalanceService.creditAmountToAccount(account, (depositAmount.getAmount() + interest));
        Transaction transaction=null;
        if (isBalanceCredit) {
            transaction = transactionService.createTransaction(Transaction.builder().amount(depositAmount.getAmount()).fromUser(account.getCustomer()).creditSuccess(true).bankName(depositAmount.getBankName()).transactionType(TransactionType.CREDIT).build());
            transaction.setCreditAmountFromUser(account.getCustomer().getUsername());
            transaction.setDebitAmountFromUser(account.getCustomer().getUsername());
        }
        return transaction;
    }

    @Transactional
    public Transaction withdrawAmount(WithdrawAmount withdrawAmount) {
        Account account = accountRepository.getSavingsAccountByUser(withdrawAmount.getCustomerId(), true, withdrawAmount.getAccountType());
        double amount = withdrawAmount.getAmount()+ (withdrawAmount.getAmount() * interestRate) * .001;
        boolean isBalanceDebit = accountBalanceService.debitAmountFromAccount(account, amount);
        Transaction transaction = null;
        if (isBalanceDebit) {
            transaction = transactionService.createTransaction(Transaction.builder().amount(withdrawAmount.getAmount()).fromUser(account.getCustomer()).creditSuccess(true).bankName(withdrawAmount.getBankName()).transactionType(TransactionType.DEBIT).build());
            transaction.setCreditAmountFromUser(account.getCustomer().getUsername());
            transaction.setDebitAmountFromUser(account.getCustomer().getUsername());
        }
        return transaction;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<AccountBalance> transferAmountSavingsToCurrentAccount(CreditTransferRequest transferAmount) {
        List<AccountBalance> accountBalanceList = new ArrayList<>();
        if(transferAmount.getDebtorId() == transferAmount.getCreditorId()) {
            List<Account> accounts = accountRepository.getAccountsByCustomerId(transferAmount.getCreditorId(), true);
            boolean isBalanceDeduct = false, isBalanceCredit = false;
            Transaction transaction = null;
            for (Account account : accounts) {
                AccountBalance accountBalance = new AccountBalance();
                accountBalance.setAccountType(account.getAccountType());
                if (account.getAccountType().equals(AccountType.SAVINGS)) {
                    isBalanceDeduct = accountBalanceService.debitAmountFromAccount(account,transferAmount.getAmount());
                    accountBalance.setAmount(account.getAmount());
                    transaction = Transaction.builder().amount(transferAmount.getAmount()).fromUser(account.getCustomer()).debitSuccess(isBalanceDeduct).bankName(transferAmount.getBankName()).transactionType(TransactionType.INTERNAL_TRANSFER).build();
                } else {
                    if (isBalanceDeduct) {
                        isBalanceCredit = accountBalanceService.creditAmountToAccount(account, transferAmount.getAmount());
                        accountBalance.setAmount(account.getAmount());
                        transaction.setToUser(account.getCustomer());
                        transaction.setCreditSuccess(isBalanceCredit);
                    }
                }
                transactionService.createTransaction(transaction);
                accountBalanceList.add(accountBalance);
            }
        }
        return accountBalanceList;
    }

    @Transactional
    public boolean transferAmountBWUsers(CreditTransferRequest transferAmount) {
        List<Integer> ids = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();
        ids.add(transferAmount.getCreditorId());
        ids.add(transferAmount.getDebtorId());
        List<Account> accounts =  accountRepository.getAccountByIds(ids , true);
        Optional<Account> debtorsAccount = accounts.stream().filter(e-> e.getCustomer().getId() == transferAmount.getDebtorId() && e.getAccountType().equals(AccountType.CURRENT)).findFirst();
        Optional<Account> creditorsAccount = accounts.stream().filter(e-> e.getCustomer().getId() == transferAmount.getCreditorId() && e.getAccountType().equals(AccountType.SAVINGS)).findFirst();
        Account  debtorAccount=null , creditorAccount= null;
        Transaction transaction= null;
        if(debtorsAccount.isPresent()){
            debtorAccount = debtorsAccount.get();
            transaction = Transaction.builder().amount(transferAmount.getAmount()).fromUser(debtorAccount.getCustomer()).bankName(transferAmount.getBankName()).transactionType(TransactionType.TRANSFER).build();
            if(debtorAccount.getAmount() >= (transferAmount.getAmount() + ((transferAmount.getAmount() * interestRate)/100))){
                boolean isBalanceDeduct = accountBalanceService.debitAmountFromAccount(debtorAccount , transferAmount.getAmount() + ((transferAmount.getAmount() * interestRate)/100));
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
            accountBalanceService.creditAmountToAccount(creditorAccount , transferAmount.getAmount() + ((creditorAccount.getAmount()+transferAmount.getAmount())*interestRate)/100);
        }else{
            throw new NotValidException("Creditors Account not found");
        }
        transactionRepository.save(transaction);
        sendNotification(transferAmount,debtorAccount.getCustomer() , creditorAccount.getCustomer());
        return true;
    }

    public void sendNotification(CreditTransferRequest transferAmount, Customer fromUser, Customer toUser) {
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
