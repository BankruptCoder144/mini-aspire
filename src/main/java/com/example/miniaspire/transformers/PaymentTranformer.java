package com.example.miniaspire.transformers;

import com.example.miniaspire.dto.PaymentDto;
import com.example.miniaspire.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentTranformer {

    public PaymentDto transformPaymentToPaymentDto (Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setId(payment.getId());
        paymentDto.setStatus(payment.getStatus());
        paymentDto.setDueDate(payment.getDueDate());
        return paymentDto;
    }
}
