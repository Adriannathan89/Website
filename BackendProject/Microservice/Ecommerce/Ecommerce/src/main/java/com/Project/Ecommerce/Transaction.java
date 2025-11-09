package com.Project.Ecommerce;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-transaction")
    private User user;
    private int TotalPrice;

    private String TransactionStatus;

    @ManyToOne
    @JoinColumn(name = "transaction_log_id",  nullable = false)
    @JsonBackReference(value = "transaction-transactionLog")
    private TransactionLog transactionLog;


}
