package com.example.miniaspire.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CustomerDto {
    int id;
    String firstName;
    String lastName;
}
