package com.bankx.api;

import com.bankx.entites.Customer;
import com.bankx.entites.Transaction;
import com.bankx.enums.AccountType;
import com.bankx.models.AccountBalance;
import com.bankx.models.CreditTransferRequest;
import com.bankx.models.DepositAmountRequest;
import com.bankx.models.WithdrawAmountRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<AccountBalance> accountBalances;

    private Customer customer;
     private DepositAmountRequest depositAmountRequest;
     private Transaction transaction;
     private  WithdrawAmountRequest withdrawAmountRequest;
     private CreditTransferRequest creditTransferRequest;

    @Before
    public void setUp() {
        customer = Customer.builder().username("test").dob(new Date()).state("UP").city("Muzaffarnagar").email("abhi@gmail.com").accounts(new ArrayList<>()).build();
    }


    @Test
    public void testCheckAmount() throws Exception {
        String jsonObject=null;
        try {
            jsonObject = objectMapper.writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                            .get("http://localhost:7979/account/checkAmount/{customerId}",1 )
                            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            accountBalances =(List<AccountBalance>)objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), List.class);
            Assert.assertNotNull(accountBalances);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testDepositAmount() throws Exception {
        depositAmountRequest = DepositAmountRequest.builder().customerId(1).amount(10).bankName("bankX").accountType(AccountType.SAVINGS).build();
        String jsonObject=null;
        try {
            jsonObject = objectMapper.writeValueAsString(depositAmountRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        MockHttpServletRequestBuilder requestBuilder = put("/account/depositAmount").contentType(MediaType.APPLICATION_JSON).content(jsonObject);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void testWithDrawlAmount() throws Exception {
        withdrawAmountRequest = WithdrawAmountRequest.builder().accountType(AccountType.CURRENT).amount(10).bankName("bankX").customerId(1).build();
        String jsonObject=null;
        try {
            jsonObject = objectMapper.writeValueAsString(withdrawAmountRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        MockHttpServletRequestBuilder requestBuilder = put("http://localhost:7979/account/withdrawAmount").contentType(MediaType.APPLICATION_JSON).content(jsonObject);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void testInterTransfer() throws Exception {
        creditTransferRequest = CreditTransferRequest.builder().creditorId(1).debtorId(1).amount(10).bankName("bankX").build();
        String jsonObject=null;
        try {
            jsonObject = objectMapper.writeValueAsString(creditTransferRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        MockHttpServletRequestBuilder requestBuilder = put("http://localhost:7979/account/intertransfer").contentType(MediaType.APPLICATION_JSON).content(jsonObject);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void testCreditTransfer() throws Exception {
        creditTransferRequest = CreditTransferRequest.builder().creditorId(2).debtorId(1).amount(10).bankName("bankX").build();
        String jsonObject=null;
        try {
            jsonObject = objectMapper.writeValueAsString(creditTransferRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        MockHttpServletRequestBuilder requestBuilder = put("http://localhost:7979/account/creditTransfer").contentType(MediaType.APPLICATION_JSON).content(jsonObject);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }
}