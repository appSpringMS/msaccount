package com.msaccount.models.dto;

import com.msaccount.models.enums.MovementType;
import lombok.Data;


@Data
public class AccountMovementRequest {
    private Double amount;
    private MovementType movementType;
    private String accountNumber;
}
