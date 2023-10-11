package com.example.miniaspire.transformers;

import com.example.miniaspire.dto.LoanDto;
import com.example.miniaspire.dto.PaymentDto;
import com.example.miniaspire.entities.Loan;
import com.example.miniaspire.entities.Payment;
import com.example.miniaspire.entities.Status;
import com.example.miniaspire.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LoanTransformer {

    @Autowired
    PaymentTranformer paymentTranformer;

    public LoanDto transformLoanToLoanDto(Loan loan) {
        LoanDto loanDto = new LoanDto();
        loanDto.setId(loan.getId());
        loanDto.setLoanAmount(loan.getTotalAmount());
        loanDto.setTerms(loan.getTerms());
        loanDto.setDate(loan.getCreationDate().toString());
        loanDto.setStatus(loan.getStatus());
        loanDto.setCustomerId(loan.getCustomer().getId());
        List<Payment> paymentList = loan.getPayments();
        List<PaymentDto> paymentDtos = new ArrayList<>();
        paymentList.forEach(payment -> {
            paymentDtos.add(paymentTranformer.transformPaymentToPaymentDto(payment));
        });
        loanDto.setPayments(paymentDtos);
        return loanDto;
    }

    public Loan transformLoanDtoToLoan(LoanDto loanDto) {
        Date loanDate = DateUtils.getDateFromString(loanDto.getDate());
        Loan loan = new Loan();
        loan.setStatus(Status.PENDING);
        loan.setCreationDate(loanDto.getDate());
        loan.setTotalAmount(loanDto.getLoanAmount());
        loan.setTerms(loanDto.getTerms());
        loan.setPayments(generatePayments(loanDto, loanDate));
        return loan;
    }

    public List<LoanDto> transformListOfLoanToDto(List<Loan> loans) {
        List<LoanDto> loanDtos = new ArrayList<>();
        loans.forEach(loan -> {
            loanDtos.add(transformLoanToLoanDto(loan));
        });
        return loanDtos;
    }

    private List<Payment> generatePayments(LoanDto loanDto, Date loanDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(loanDate);
        List<Payment> payments = new ArrayList<>(loanDto.getTerms());
        int terms = loanDto.getTerms();
        float sum = 0;
        for (int i=0;i<terms;i++) {
            float paymentAmount = loanDto.getLoanAmount()/terms;
            if(i==terms-1) {
                paymentAmount = loanDto.getLoanAmount() - sum; //Add remaining amount to last Installment
            }
            sum += paymentAmount;
            Payment payment = new Payment();
            payment.setAmount(paymentAmount);
            cal.add(Calendar.DATE, 7);
            payment.setDueDate(DateUtils.getDateString(cal.getTime()));
            payment.setStatus(Status.PENDING);
            payments.add(payment);

        }
        return payments;
    }
}
