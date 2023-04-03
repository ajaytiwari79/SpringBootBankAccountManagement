package com.bankx.services.customerService;

import com.bankx.entites.account.Account;
import com.bankx.entites.account.AccountType;
import com.bankx.entites.customer.Customer;
import com.bankx.repositories.customerRepository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${defaultBalanceForSavingsAccount}")
    private double defaultBalanceForSavingsAccount;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addNewCustomer(Customer customer) {
        customer.addAccount(Account.builder().accountStatus(true).accountType(AccountType.SAVINGS).amount(defaultBalanceForSavingsAccount).build());
        customer.addAccount(Account.builder().accountStatus(true).accountType(AccountType.CURRENT).build());
        return customerRepository.save(customer);
    }


}
