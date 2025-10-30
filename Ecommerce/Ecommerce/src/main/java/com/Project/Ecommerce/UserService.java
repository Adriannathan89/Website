package com.Project.Ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionLogRepository transactionLogRepository;

    public int AddCart(String username, AddProduct newAddProduct) {
        User currentUser = userRepository.findByUsername(username);
        Product itemInfo = productRepository.findByProductName(newAddProduct.getName());
        Cart currentCart = currentUser.getCart();

        if(currentUser != null && itemInfo != null) {
            if(itemInfo.getStock() >= newAddProduct.getQuantity()) {
                CartItem thisItem = currentCart.findCurrItem(newAddProduct.getName());
                if(thisItem != null) {
                    thisItem.setQuantity(thisItem.getQuantity() + newAddProduct.getQuantity());
                } else{
                    CartItem createItem = new CartItem();
                    createItem.setProductName(newAddProduct.getName());
                    createItem.setQuantity(newAddProduct.getQuantity());

                    currentCart.addItem(createItem);
                    currentUser.setCart(currentCart);
                }
                itemInfo.setStock(itemInfo.getStock() - newAddProduct.getQuantity());

                userRepository.save(currentUser);
                return 1;
            }
        }
        return -1;
    }

    public int UserCheckout(String username, int day) {
        int totalPrice = 0;
        User currentUser = userRepository.findByUsername(username);
        Cart currentCart = currentUser.getCart();
        List<CartItem> productFinal = currentCart.getCartItems();

        for(int i = 0; i < productFinal.size(); i++){
            int priceProduct = productRepository.findByProductName(productFinal.get(i).getProductName()).getPrice();
            int QTYProduct = productFinal.get(i).getQuantity();

            totalPrice += priceProduct * QTYProduct;
        }
        currentCart.getCartItems().clear();
        cartRepository.save(currentCart);

        Transaction userTransaction = new Transaction();
        userTransaction.setUser(currentUser);
        userTransaction.setTotalPrice(totalPrice);
        userTransaction.setTransactionStatus("Waiting for The Payment");

        TransactionLog todayLog = transactionLogRepository.findByDays(day);
        todayLog.addTransaction(userTransaction);
        currentUser.createTransaction(userTransaction);

        if(todayLog != null) {
            transactionLogRepository.save(todayLog);
            userRepository.save(currentUser);
            return totalPrice;
        }

        return -1;
    }

    public int UserMakePayment(String username, int day) {
        User currentUser = userRepository.findByUsername(username);
        Transaction userTransaction = transactionRepository.findByUser(currentUser);
        TransactionLog todayLog = transactionLogRepository.findByDays(day);
        todayLog.setIncome(todayLog.getIncome() + userTransaction.getTotalPrice());

        if(currentUser != null && todayLog != null) {
            userTransaction.setTransactionStatus("Paymeny Sucess");
            transactionLogRepository.save(todayLog);
            currentUser.getUserTransaction().clear();
            userRepository.save(currentUser);
            return 1;
        }
        return -1;
    }

    public int UserRemoveItem(String username, String ProductName) {
        User currentUser = userRepository.findByUsername(username);
        Cart currentCart = currentUser.getCart();
        Product currentProduct = productRepository.findByProductName(ProductName);
        CartItem itemFind = currentCart.findCurrItem(ProductName);

        if(itemFind != null) {
            currentCart.RemoveItem(itemFind.getProductName());
            currentProduct.setStock(currentProduct.getStock() + itemFind.getQuantity());
            cartItemRepository.delete(itemFind);
            productRepository.save(currentProduct);
            return 1;
        }
        return -1;
    }

    public Cart ShowMyProducts(String username) {
        User currentUser = userRepository.findByUsername(username);
        return currentUser.getCart();
    }
}
