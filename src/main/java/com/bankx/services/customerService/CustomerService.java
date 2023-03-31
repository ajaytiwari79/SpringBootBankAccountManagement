package com.bankx.services.customerService;

import com.bankx.dtos.customerDtos.CustomerDTO;
import com.bankx.models.customer.Customer;

public interface CustomerService {
    Customer addNewCustomer(CustomerDTO customerDTO);
}
