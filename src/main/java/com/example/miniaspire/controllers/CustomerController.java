package com.example.miniaspire.controllers;


import com.example.miniaspire.dto.LoanDto;
import com.example.miniaspire.dto.RepaymentDto;
import com.example.miniaspire.exceptions.AppException;
import com.example.miniaspire.handlers.CustomerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    @Autowired
    CustomerHandler customerHandler;

    @PostMapping("/loan")
    public LoanDto createLoan(@RequestBody LoanDto loan) {
        return customerHandler.createLoan(loan);
    }

    @GetMapping("/{cid}/loan")
    public List<LoanDto> getAllLoansByCustomerId(@PathVariable("cid") int cid) {
        return customerHandler.getAllLoans(cid);
    }

    @GetMapping("/{cid}/loan/{lid}")
    public Optional<LoanDto> getLoanById(@PathVariable("lid") int lid, @PathVariable("cid") int cid) throws AppException {
        return customerHandler.getLoanDetailsById(lid, cid);
    }
    @PutMapping("/payment")
    public void repayAmount(@RequestBody RepaymentDto repaymentDto) throws AppException {
        customerHandler.repayLoanAmount(repaymentDto);
    }
}
