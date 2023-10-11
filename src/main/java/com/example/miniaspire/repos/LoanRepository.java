package com.example.miniaspire.repos;

import com.example.miniaspire.entities.Loan;
import com.example.miniaspire.entities.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Integer> {

    @Modifying
    @Transactional
    @Query("update Loan l set l.status =:status where l.id =:loanId")
    void setStatusById(@Param("loanId") int loanId, @Param("status") Status status);

    List<Loan> findAllByCustomerId(int customer_id);

}
