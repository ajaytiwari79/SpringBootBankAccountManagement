package com.bankx.repositories.transactionsRepository;

import com.bankx.models.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction , Integer> {
    @Query("select t from Transaction t where t.bankName = ?1 and createdAt between ?2 and ?3")
    List<Transaction> getAllTransactionByBANKZToday(String bankz , Date startDate, Date endDate);
}
