package com.bankx.repositories;

import com.bankx.entites.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction , Integer> {
    @Query("select t from Transaction t where t.bankName = ?1 and createdAt between ?2 and ?3")
    List<Transaction> getAllTransactionByBANKZToday(String bankz , LocalDate startDate, LocalDate endDate);
}
