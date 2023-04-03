package com.bankx.services;

import com.bankx.repositories.CustomerRepository;
import com.bankx.services.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setCustomerRepository(){
        this.customerService = new CustomerServiceImpl(this.customerRepository);
    }

}