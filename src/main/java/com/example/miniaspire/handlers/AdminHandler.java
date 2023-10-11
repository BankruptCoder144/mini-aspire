package com.example.miniaspire.handlers;


import com.example.miniaspire.dto.CustomerDto;
import com.example.miniaspire.dto.LoanDto;
import com.example.miniaspire.entities.Customer;
import com.example.miniaspire.entities.Loan;
import com.example.miniaspire.entities.Status;
import com.example.miniaspire.exceptions.AppException;
import com.example.miniaspire.repos.CustomerRepository;
import com.example.miniaspire.repos.LoanRepository;
import com.example.miniaspire.transformers.CustomerTransformer;
import com.example.miniaspire.transformers.LoanTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminHandler {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerTransformer customerTransformer;

    @Autowired
    LoanTransformer loanTransformer;

    public void approveLoanById(int lid) throws AppException {
        Optional<Loan> loanOptional = loanRepository.findById(lid);
        if (loanOptional.isPresent() && loanOptional.get().getStatus() == Status.PENDING){
            loanRepository.setStatusById(lid, Status.APPROVED);
        } else  {
            throw new AppException("Loan doesn't exist or is not in PENDING states",
                    HttpStatus.BAD_REQUEST);
        }
    }

    public Customer addCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        return customerRepository.save(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        return customerTransformer.transformListOfCustomerToCustomerDto(customerRepository.findAll());
    }

    public List<LoanDto> getAllLoans() {
        return loanTransformer.transformListOfLoanToDto((List<Loan>) loanRepository.findAll());
    }
}
