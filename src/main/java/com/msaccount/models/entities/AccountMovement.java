package com.msaccount.models.entities;

import com.msaccount.models.enums.MovementType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "account_movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class AccountMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_movement_id;

    private Double amount;

    private Double balance;

    private MovementType movementType;

    private LocalDate transactionDate;

    @ManyToOne
    @JoinColumn(name = "accountNumber", nullable = false)
    private Account account;

    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDate.now();
    }
}
