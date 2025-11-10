package com.Project.Product;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    @PostMapping({"/addProduct"})
    public int addNewProduct(@RequestBody Product newProduct) {
        return productService.createProduct(newProduct);
    }

    @PutMapping({"/addStock/{id}/{QTY}"})
    public void addStock(@PathVariable Long id, @PathVariable int QTY) {
        productService.addStock(id, QTY);
    }

    @GetMapping({"/showAllProd"})
    public List<ProductResponseUser> showAllProd() {
        return productService.getAllProduct();
    }

    @GetMapping({"/Admin/viewStorage"})
    public List<ProductResponseAdmin> viewStorage() {
        return productService.viewStorage();
    }

    @PostMapping({"/productRequest"})
    public ResponseEntity<?> productRequest(@RequestBody Map<String, Object> requestInfo) {
        Long id = ((Number)requestInfo.get("productId")).longValue();
        int amount = (Integer)requestInfo.get("amount");
        return productService.productRequestUser(id, amount);
    }
}
