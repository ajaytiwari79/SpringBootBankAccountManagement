package com.bankx.models.transactions;

import com.bankx.models.customer.Customer;
import com.bankx.utility.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Transaction extends BaseEntity {
    private TransactionType transactionType;
    private boolean debitSuccess;
    private boolean creditSuccess;
    private double amount;
    private boolean enables;
    @ManyToOne
    @JoinColumn(name = "fromUser")
    private Customer fromUser;
    @ManyToOne
    @JoinColumn(name = "toUser")
    private Customer toUser;
    private String bankName;
}
