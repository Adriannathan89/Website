package com.Project.Ecommerce;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionLogRepository transactionLogRepository;
    @Autowired
    UserService userService;

    @PostMapping("/makeTransactionLog/{day}")
    public int makeTransactionLog(@PathVariable int day) {
        TransactionLog newTransactionLog = new TransactionLog();
        newTransactionLog.setDays(day);
        transactionLogRepository.save(newTransactionLog);
        return 1;
    }

    @PostMapping("/UserRegist")
    public int AddUser(@RequestBody User newUser){
        User Available = userRepository.findByUsername(newUser.getUsername());
        if(Available == null) {
            Cart newCart = new Cart();
            newUser.setCart(newCart);
            userRepository.save(newUser);
            return 1;
        }
        return -1;

    }
    @Transactional
    @PostMapping("/AddProduct")
    public int AddProduct(@RequestBody Product newProduct) {
        Product Available = productRepository.findByProductName(newProduct.getProductName());
        if(Available == null) {
            productRepository.save(newProduct);
            return 1;
        }
        return -1;
    }

    @Transactional
    @PostMapping("/{username}/MakePayment/{day}")
    public int userPayment(@PathVariable String username, @PathVariable int day) {
        return userService.UserMakePayment(username, day);
    }

    @Transactional
    @PostMapping("/{username}/CheckOut/{day}")
    public int UserCheckOut(@PathVariable String username, @PathVariable int day) {
        return userService.UserCheckout(username, day);
    }

    @Transactional
    @PostMapping("/{username}/addToCart")
    public int addToCart(@RequestBody AddProduct newAddProduct, @PathVariable String username) {
        return userService.AddCart(username, newAddProduct);
    }

    @Transactional
    @PutMapping("/{username}/removeItemCart/{productName}")
    public int UserRemove(@PathVariable String username, @PathVariable String productName) {
        return userService.UserRemoveItem(username, productName);
    }

    @GetMapping("/{username}/ShowCart")
    public Cart ShowMyCart(@PathVariable String username) {
        User currentUser = userRepository.findByUsername(username);
        if(currentUser != null) {
            return userService.ShowMyProducts(currentUser.getUsername());
        }
        return null;
    }

    @GetMapping("/ShowTransaction/{day}")
    public TransactionLog ShowLog(@PathVariable int day){
        TransactionLog transactionLog = transactionLogRepository.findByDays(day);
        return transactionLog;
    }

}
