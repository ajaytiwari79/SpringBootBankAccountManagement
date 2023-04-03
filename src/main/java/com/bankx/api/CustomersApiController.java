package com.bankx.api;

import com.bankx.entites.Customer;
import com.bankx.services.CustomerService;
import com.bankx.utilities.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomersApiController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/registerCustomer")
    public ResponseEntity<Object> registerCustomer(@RequestBody Customer customer){
        return ResponseHandler.generateResponse(HttpStatus.OK , true , customerService.registerCustomer(customer));
    }
}
