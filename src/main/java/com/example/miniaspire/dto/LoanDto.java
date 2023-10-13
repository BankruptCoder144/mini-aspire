package com.example.miniaspire.dto;

import com.example.miniaspire.entities.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class LoanDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    int loanId;

    int customerId;

    float loanAmount;

    int terms;

    String date;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    List<PaymentDto> payments;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Status status;

    public LoanDto() {
        payments = new ArrayList<>();
    }

    public void addPayment(PaymentDto payment) {
        payments.add(payment);
    }
}
