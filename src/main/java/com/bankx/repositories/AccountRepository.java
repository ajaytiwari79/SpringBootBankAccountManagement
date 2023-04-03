package com.bankx.repositories;

import com.bankx.models.AccountBalance;
import com.bankx.entites.Account;
import com.bankx.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    @Query("select new com.bankx.models.AccountBalance(account.accountType , account.amount) from Account account where account.customer.id =?1 and accountStatus=?2")
    List<AccountBalance> getAccountBalance(int customerId , boolean status);

    @Query("select account from Account account where account.customer.id =?1 and accountStatus=?2 and accountType = ?3")
    Account getSavingsAccountByUser(int customerId , boolean status , AccountType accountType);

    @Query("select account from Account account where account.customer.id=?1 and accountStatus=?2")
    List<Account> getAccountsByCustomerId(int customerId, boolean active);

    @Query("select account from Account account where account.customer.id in ?1 and accountStatus=?2")
    List<Account> getAccountByIds(List<Integer> ids, boolean active);
}
