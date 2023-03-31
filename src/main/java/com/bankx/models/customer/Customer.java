package com.bankx.models.customer;

import com.bankx.models.account.Account;
import com.bankx.models.transactions.Transaction;
import com.bankx.utility.BaseEntity;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Customer extends BaseEntity {
    private String username;
    private String email;
    private Date dob;
    private String contactNo;
    private String street;
    private String city;
    //As aadhar in india
    private String uniqueId;
    private String state;
    private String pinCode;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_id", referencedColumnName = "id")
    private List<Account> accounts;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_id", referencedColumnName = "id")
    private List<Transaction> transactions;

    public Customer(int id, boolean deleted, String username, String email, Date dob, String contactNo, String street, String city, String uniqueId, String state, String pinCode) {
        super(id, deleted);
        this.username = username;
        this.email = email;
        this.dob = dob;
        this.contactNo = contactNo;
        this.street = street;
        this.city = city;
        this.uniqueId = uniqueId;
        this.state = state;
        this.pinCode = pinCode;
    }
}
