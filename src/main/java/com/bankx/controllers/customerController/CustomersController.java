package com.bankx.controllers.customerController;

import com.bankx.models.customer.Customer;
import com.bankx.services.customerService.CustomerService;
import com.bankx.services.customerService.CustomerServiceImpl;
import com.bankx.utility.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomersController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add_new_customer")
    public ResponseEntity<Object> addNewCustomer(@RequestBody Customer customer){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , customerService.addNewCustomer(customer));
    }
}
