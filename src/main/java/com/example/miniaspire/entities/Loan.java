package com.example.miniaspire.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;
    @Column(name = "totalAmount")
    float totalAmount;

    @Column(name = "terms")
    int terms;

    @OneToMany(targetEntity = Payment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_id",nullable = false)
    List<Payment> payments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id", nullable=false , insertable=false, updatable=false)
    Customer customer;

    String creationDate;

    @Enumerated(EnumType.STRING)
    Status status;

}
