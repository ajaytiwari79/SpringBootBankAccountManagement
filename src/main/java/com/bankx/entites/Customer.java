package com.bankx.entites;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
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
    @ApiModelProperty(hidden = true)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private List<Account> accounts= new ArrayList<>();
    @ApiModelProperty(hidden = true)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private List<Transaction> transactions;

    public boolean addAccount(Account account){
        this.accounts.add(account);
        return false;
    }

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
