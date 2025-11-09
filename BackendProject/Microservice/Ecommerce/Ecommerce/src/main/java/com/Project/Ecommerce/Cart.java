package com.Project.Ecommerce;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "cart-cartitem")
    List<CartItem> cartItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference(value = "user-cart")
    private User user;

    public void addItem(CartItem newCartItem) {
        cartItems.add(newCartItem);
        newCartItem.setCart(this);
    }

    public int RemoveItem(String productName) {
        for(int i = 0; i < cartItems.size(); i++) {
            if(cartItems.get(i).getProductName().equals(productName)) {
                cartItems.remove(i);
                return 1;
            }
        }
        return -1;
    }

    public CartItem findCurrItem(String find) {
        for(int i = 0; i < cartItems.size(); i++) {
            if(cartItems.get(i).getProductName().equals(find)) {
                return cartItems.get(i);
            }
        }
        return null;
    }
}
