package com.example.miniaspire.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String firstName;

    String lastName;

    @OneToMany(targetEntity = Loan.class, cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id", nullable = false)
    List<Loan> loans;

}
