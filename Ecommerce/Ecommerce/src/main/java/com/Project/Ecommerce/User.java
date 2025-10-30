package com.Project.Ecommerce;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-transaction")
    private List<Transaction> userTransaction = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "user-cart")
    private Cart cart;

    public void setCart(Cart newCart) {
        this.cart = newCart;
        newCart.setUser(this);
    }

    public void createTransaction(Transaction newTransacton) {
        this.userTransaction.add(newTransacton);
        newTransacton.setUser(this);
    }
}
