package com.Project.Ecommerce;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String productName;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false, unique = true)
    @JsonBackReference(value = "cart-cartitem")
    private Cart cart;

    public void MakeProduct(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

}
