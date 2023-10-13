package com.example.miniaspire.dto;

import com.example.miniaspire.entities.Status;
import lombok.Data;

@Data
public class PaymentDto {
    int paymentId;
    float amount;
    String dueDate;
    Status status;
}
