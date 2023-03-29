package com.bankx.repositories.accountRepositry;

import com.bankx.dtos.accountDtos.AccountBalance;
import com.bankx.models.account.Account;
import com.bankx.models.account.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    @Query("select new com.bankx.dtos.accountDtos.AccountBalance(account.accountType , account.balance) from Account account where c_id =?1 and accountStatus=?2")
    List<AccountBalance> getAccountBalance(int c_id , boolean status);

    @Query("select account from Account account where c_id =?1 and accountStatus=?2 and accountType = ?3")
    Account getSavingsAccountBalanceByUser(int c_id , boolean status , AccountType accountType);

    @Query("select account from Account account where c_id=?1 and accountStatus=?2")
    List<Account> getAccountsByCustomerId(int c_id, boolean active);

    @Query("select account from Account account where c_id in ?1 and accountStatus=?2")
    List<Account> getAccountByIds(List<Integer> ids, boolean active);
}
