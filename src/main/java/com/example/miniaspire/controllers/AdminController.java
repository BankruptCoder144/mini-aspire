package com.example.miniaspire.controllers;


import com.example.miniaspire.dto.CustomerDto;
import com.example.miniaspire.dto.LoanDto;
import com.example.miniaspire.entities.Customer;
import com.example.miniaspire.exceptions.AppException;
import com.example.miniaspire.handlers.AdminHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    @Autowired
    AdminHandler adminHandler;

    @PutMapping("/approve/{id}")
    public void approveLoan(@PathVariable("id") int lid) throws AppException {
        adminHandler.approveLoanById(lid);
    }

    @PostMapping("/customer")
    public Customer addCustomer(@RequestBody CustomerDto customerDto) {
        return adminHandler.addCustomer(customerDto);
    }

    @GetMapping("/customer")
    public List<CustomerDto> getAllCutomers() {
        return adminHandler.getAllCustomers();
    }

    @GetMapping("/loan")
    public List<LoanDto> getAllLoans() {
        return adminHandler.getAllLoans();
    }
}
