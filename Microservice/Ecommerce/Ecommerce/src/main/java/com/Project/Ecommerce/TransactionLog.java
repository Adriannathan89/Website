package com.Project.Ecommerce;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int days;

    @OneToMany(mappedBy = "transactionLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "transaction-transactionLog")
    List<Transaction> transactions = new ArrayList<>();

    private int income;

    public void addTransaction(Transaction newTransaction) {
        transactions.add(newTransaction);
        newTransaction.setTransactionLog(this);
    }
}
