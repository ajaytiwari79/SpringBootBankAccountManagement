package com.bankx.services.impl;

import com.bankx.entites.Account;
import com.bankx.enums.AccountType;
import com.bankx.entites.Customer;
import com.bankx.repositories.CustomerRepository;
import com.bankx.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${defaultBalanceForSavingsAccount}")
    private double defaultBalanceForSavingsAccount;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer registerCustomer(Customer customer) {
        customer.addAccount(Account.builder().accountStatus(true).accountType(AccountType.SAVINGS).amount(defaultBalanceForSavingsAccount).build());
        customer.addAccount(Account.builder().accountStatus(true).accountType(AccountType.CURRENT).build());
        return customerRepository.save(customer);
    }


}
