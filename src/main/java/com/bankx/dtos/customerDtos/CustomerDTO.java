package com.bankx.dtos.customerDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
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
}
