package com.msaccount.models.dto;

import com.msaccount.models.enums.AccountType;
import com.msaccount.models.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountMovementResponse {
    private Long account_movement_id;
    private String accountNumber;
    private AccountType accountType;
    private Double initialBalance;
    private Long customerId;
    private Double amount;
    private Double balance;
    private MovementType movementType;
    private LocalDate transactionDate;
}
