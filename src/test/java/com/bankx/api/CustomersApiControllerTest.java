package com.bankx.api;

import com.bankx.entites.Customer;
import com.bankx.models.AccountBalance;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomersApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<AccountBalance> accountBalances;

    private Customer customer;

    @Before
    public void setUp() {
         customer = Customer.builder().username("test").dob(new Date()).state("UP").city("Muzaffarnagar").email("abhi@gmail.com").accounts(new ArrayList<>()).build();
    }

    public void registerCustomer() {
        String jsonObject = null;
        try {
            jsonObject = objectMapper.writeValueAsString(customer);
        } catch (
                JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:7979/customer/registerCustomer")
                            .contentType(MediaType.APPLICATION_JSON).content(jsonObject)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            customer = objectMapper.readValue(resultActions.getResponse().getContentAsByteArray(), Customer.class);
            Assert.assertNotNull(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}