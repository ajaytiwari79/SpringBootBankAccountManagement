package com.bankx.services.customerService;

import com.bankx.dtos.customerDtos.CustomerDTO;
import com.bankx.models.account.Account;
import com.bankx.models.account.AccountType;
import com.bankx.models.customer.Customer;
import com.bankx.repositories.customerRepository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${defaultBalanceForSavingsAccount}")
    private double defaultBalanceForSavingsAccount;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addNewCustomer(CustomerDTO customerDTO) {
        Customer customer = Customer.builder().dob(customerDTO.getDob()).email(customerDTO.getEmail()).city(customerDTO.getCity()).state(customerDTO.getState()).street(customerDTO.getStreet()).contactNo(customerDTO.getContactNo()).pinCode(customerDTO.getPinCode()).uniqueId(customerDTO.getUniqueId()).username(customerDTO.getUsername()).build();
        ArrayList<Account> accountList = new ArrayList<>();
        accountList.add(Account.builder().accountStatus(true).accountType(AccountType.SAVINGS).balance(defaultBalanceForSavingsAccount).build());
        accountList.add(Account.builder().accountStatus(true).accountType(AccountType.CURRENT).build());
        customer.setAccounts(accountList);
        return customerRepository.save(customer);
    }


}
