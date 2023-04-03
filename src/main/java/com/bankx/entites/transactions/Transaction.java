package com.bankx.entites.transactions;

import com.bankx.entites.customer.Customer;
import com.bankx.utility.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

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
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fromUser")
    private Customer fromUser;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "toUser")
    private Customer toUser;
    private String bankName;
    @Transient
    @JsonProperty
    private String debitAmountFromUser;
    @Transient
    @JsonProperty
    private String creditAmountFromUser;

    public Transaction(double amount, boolean creditSuccess, String bankName, Customer customer, TransactionType transactionType) {
        this.amount=amount;
        this.creditSuccess=creditSuccess;
        this.bankName=bankName;
        this.toUser=customer;
        this.transactionType=transactionType;
    }
}
