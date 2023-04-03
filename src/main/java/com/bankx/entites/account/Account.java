package com.bankx.entites.account;

import com.bankx.entites.customer.Customer;
import com.bankx.utility.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Account extends BaseEntity {
    private AccountType accountType;
    private double amount;
    private boolean accountStatus;
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}
