package com.msaccount.repositories;

import com.msaccount.models.entities.Account;
import com.msaccount.models.entities.AccountMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AccountMovementRepository extends JpaRepository<AccountMovement, Long> {
    List<AccountMovement> findAllByTransactionDateBetweenAndAccount(LocalDate transactionDate, LocalDate transactionDate2, Account account);
}
