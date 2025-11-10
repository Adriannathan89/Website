package com.project.cart;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;

    @PostMapping({"/makeCart"})
    public ResponseEntity<?> connectUser(@RequestBody Map<String, Object> requestInfo) {
        return cartService.makeCart(requestInfo);
    }

    @PostMapping({"/buyProduct"})
    public ResponseEntity<?> buyProduct(@RequestBody Map<String, Object> requestInfo) {
        Long productId = ((Number)requestInfo.get("productId")).longValue();
        Long userId = ((Number)requestInfo.get("ownerId")).longValue();
        int amount = (Integer)requestInfo.get("amount");
        return cartService.addProduct(userId, productId, amount);
    }

    @GetMapping({"/showCart/{userId}"})
    public ResponseEntity<?> showCart(@PathVariable Long userId) {
        return cartService.userCartRequest(userId);
    }
}
