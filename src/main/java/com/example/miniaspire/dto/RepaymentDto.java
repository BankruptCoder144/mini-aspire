package com.example.miniaspire.dto;

import lombok.Data;

@Data
public class RepaymentDto {
    float amount;
    int loanId;

    int customerId;
}
