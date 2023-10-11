package com.example.miniaspire.handlers;

import com.example.miniaspire.dto.LoanDto;
import com.example.miniaspire.dto.RepaymentDto;
import com.example.miniaspire.entities.Customer;
import com.example.miniaspire.entities.Loan;
import com.example.miniaspire.entities.Payment;
import com.example.miniaspire.entities.Status;
import com.example.miniaspire.exceptions.AppException;
import com.example.miniaspire.repos.CustomerRepository;
import com.example.miniaspire.repos.LoanRepository;
import com.example.miniaspire.repos.PaymentRepository;
import com.example.miniaspire.transformers.LoanTransformer;
import com.example.miniaspire.transformers.PaymentTranformer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerHandler {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    LoanTransformer loanTransformer;

    @Autowired
    PaymentTranformer paymentTranformer;

    public LoanDto createLoan(LoanDto loanDto) {
        return addLoanToCustomer(loanDto.getCustomerId(), loanTransformer.transformLoanDtoToLoan(loanDto));
    }

    @Transactional
    protected LoanDto addLoanToCustomer(int customerId, Loan loan) {
        Customer customer = customerRepository.findById(customerId).get();
        customer.getLoans().add(loan);
        customerRepository.save(customer);
        loan.setCustomer(customer);
        return loanTransformer.transformLoanToLoanDto(loan);
    }

    public List<LoanDto> getAllLoans(int customerId) {
        return loanTransformer.transformListOfLoanToDto(loanRepository.findAllByCustomerId(customerId));
    }

    public Optional<LoanDto> getLoanDetailsById(int lid, int cid) throws AppException {
        Optional<Loan> loan =  loanRepository.findById(lid);
        if(!loan.isPresent()){
            throw new AppException(String.format("LoanId %s does not exist in DB", lid), HttpStatus.BAD_REQUEST);
        }
        if(loan.get().getCustomer().getId() != cid) {
            throw new AppException(String.format("CustomerId %s can't access LoanId %s", cid, lid), HttpStatus.FORBIDDEN);
        }
        LoanDto loanDto = loanTransformer.transformLoanToLoanDto(loan.get());

        return Optional.of(loanDto);
    }

    public void repayLoanAmount(RepaymentDto repaymentDto) throws AppException {
        Optional<Loan> loanOpt = loanRepository.findById(repaymentDto.getLoanId());
        if(!loanOpt.isPresent()) {
            throw new AppException("Loan not found", HttpStatus.NOT_FOUND);
        }
        Loan loan = loanOpt.get();
        if(loan.getStatus()!= Status.APPROVED) {
            throw new AppException("Loan is either not approved or already paid", HttpStatus.BAD_REQUEST);
        }
        List<Payment> payments = loan.getPayments();
        Payment pendingPayment = payments.stream().filter(payment ->
                payment.getStatus() == Status.PENDING).findFirst().get();

        if(pendingPayment.getAmount()> repaymentDto.getAmount()) {
            throw new AppException("Amount less than the payment amount", HttpStatus.BAD_REQUEST);
        }
        pendingPayment.setStatus(Status.PAID);
        if(payments.stream().filter(payment ->
                payment.getStatus() == Status.PENDING).count()==0) {
            loan.setStatus(Status.PAID);
            loanRepository.save(loan);
        } else {
            paymentRepository.save(pendingPayment);
        }

    }

}
