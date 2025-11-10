package com.Project.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper mapper;

    public int createProduct(Product newProduct) {
        Product currentProduct = productRepository.findByName(newProduct.getName());
        if (currentProduct == null) {
            productRepository.save(newProduct);
            return 1;
        } else {
            return -1;
        }
    }

    public void addStock(Long id, int stockAdder) {
        Optional<Product> Available = productRepository.findById(id);
        Product currentProdcut = (Product)Available.get();
        currentProdcut.setStock(currentProdcut.getStock() + stockAdder);
        productRepository.save(currentProdcut);
    }

    public ProductResponseUser getProductInfo(Long id) {
        Optional<Product> currentProduct = productRepository.findById(id);
        ProductResponseUser productResponseUser = (ProductResponseUser)mapper.map(currentProduct, ProductResponseUser.class);
        return productResponseUser;
    }

    public ProductResponseAdmin adminProdcut(Long id) {
        Optional<Product> currentProduct = productRepository.findById(id);
        ProductResponseAdmin productResponseAdmin = (ProductResponseAdmin)mapper.map(currentProduct, ProductResponseAdmin.class);
        return productResponseAdmin;
    }

    public List<ProductResponseAdmin> viewStorage() {
        List<ProductResponseAdmin> storage = new ArrayList();
        List<Product> allProd = productRepository.findAll();

        for(int i = 0; i < allProd.size(); ++i) {
            storage.add(adminProdcut(((Product)allProd.get(i)).getId()));
        }

        return storage;
    }

    public ResponseEntity<?> productRequestUser(Long id, int amount) {
        Optional<Product> product = productRepository.findById(id);
        Map<String, Object> errorHandling = Map.of("Info", "Product is out off Stock", "Status", "Failed");
        if (product.isPresent()) {
            if (((Product)product.get()).getStock() >= amount) {
                Product currentProduct = (Product)product.get();
                currentProduct.setStock(currentProduct.getStock() - amount);
                Map<String, Object> response = Map.of("productName", currentProduct.getName(), "productPrice", currentProduct.getPrice(), "amount", currentProduct.getPrice(), "status", "Success");
                productRepository.save(currentProduct);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorHandling);
            }
        } else {
            errorHandling.replace("Info", "Product Not Available");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorHandling);
        }
    }

    public List<ProductResponseUser> getAllProduct() {
        List<ProductResponseUser> listOfProduct = new ArrayList();
        List<Product> allProd = productRepository.findAll();

        for(int i = 0; i < allProd.size(); ++i) {
            listOfProduct.add(getProductInfo(((Product)allProd.get(i)).getId()));
        }

        return listOfProduct;
    }
}
