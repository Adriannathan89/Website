package com.project.cart;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CartItemRepository cartItemRepository;
    String productConnect = "http://service-product:8081/";

    public ResponseEntity<?> makeCart(Map<String, Object> requestInfo) {
        Long ownerId = ((Number)requestInfo.get("ownerId")).longValue();
        Cart newCart = new Cart();
        newCart.setOwnerId(ownerId);
        cartRepository.save(newCart);
        Map<String, Object> response = Map.of("cartId", newCart.getId(), "ownerId", ownerId, "Status", "Created Successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    public ResponseEntity<?> addProduct(Long ownerId, Long productId, int amount) {
        Cart userCart = cartRepository.findByOwnerId(ownerId);
        String webConnection = productConnect + "productRequest";
        String Status = "";
        if (userCart != null) {
            Map<String, Object> requestInfo = Map.of("productId", productId, "amount", amount);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity(requestInfo, headers);
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(webConnection, request, Map.class, new Object[0]);
            Map<String, Object> responseProduct = (Map)responseEntity.getBody();
            CartItem exist = userCart.findCartItem((String)responseProduct.get("productName"));
            if (exist == null) {
                CartItem newCartItem = new CartItem();
                newCartItem.setName((String)responseProduct.get("productName"));
                newCartItem.setPrice((Integer)responseProduct.get("productPrice"));
                newCartItem.setQuantity(amount);
                newCartItem.setTotal(newCartItem.getPrice() * amount);
                userCart.getListOfItem().add(newCartItem);
                newCartItem.setCart(userCart);
                userCart.setTotalPrice(userCart.getTotalPrice() + newCartItem.getTotal());
            } else {
                exist.setTotal(exist.getTotal() + exist.getPrice() * amount);
                userCart.setTotalPrice(userCart.getTotalPrice() + exist.getTotal() - exist.getPrice() * exist.getQuantity());
                exist.setQuantity(exist.getQuantity() + amount);
            }

            cartRepository.save(userCart);
            Status = "Success";
        }

        Status = Status.equals("") ? "The Cart Not Found" : Status;
        Map<String, Object> returnStatus = Map.of("Status", Status);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(returnStatus);
    }

    public ResponseEntity<?> userCartRequest(Long id) {
        Cart userCart = cartRepository.findByOwnerId(id);
        Map<String, Object> errorHandling = Map.of("Info", "Cart not Found", "Status", "Failed");
        if (userCart != null) {
            Map<String, Object> response = Map.of("items", userCart.getListOfItem(), "totalPrice", userCart.getTotalPrice(), "Status", "Success");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorHandling);
        }
    }
}
