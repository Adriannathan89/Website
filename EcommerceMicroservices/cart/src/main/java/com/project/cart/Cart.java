package com.project.cart;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "totalPrice")
    private int totalPrice;
    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "listOfItem")
    @OneToMany(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private List<CartItem> listOfItem = new ArrayList();

    public CartItem findCartItem(String name) {
        for(int i = 0; i < this.listOfItem.size(); ++i) {
            if (((CartItem)this.listOfItem.get(i)).getName().equals(name)) {
                return (CartItem)this.listOfItem.get(i);
            }
        }
        return null;
    }
}
