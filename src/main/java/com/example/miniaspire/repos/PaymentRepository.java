package com.example.miniaspire.repos;

import com.example.miniaspire.entities.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {

    public List<Payment> findAllByLoanIdOrderByIdAsc(int loan_id);
}
