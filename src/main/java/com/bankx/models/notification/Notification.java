package com.bankx.models.notification;

import com.bankx.models.customer.Customer;
import com.bankx.models.transactions.TransactionType;
import com.bankx.utility.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends BaseEntity {
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name = "c_id")
    private Customer customer;
    private double amount;
}
