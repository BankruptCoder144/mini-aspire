package com.example.miniaspire.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CustomerDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    int customerId;
    String firstName;
    String lastName;
}
