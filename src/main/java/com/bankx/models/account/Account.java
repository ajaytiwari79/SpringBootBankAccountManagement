package com.bankx.models.account;

import com.bankx.models.customer.Customer;
import com.bankx.utility.BaseEntity;
import lombok.*;

import javax.persistence.CascadeType;
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
    private double balance;
    private boolean accountStatus;
    @ManyToOne
    @JoinColumn(name = "c_id")
    private Customer customer;
}
