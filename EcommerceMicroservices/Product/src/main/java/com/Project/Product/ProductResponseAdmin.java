package com.Project.Product;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseAdmin {
    private Long id;
    private String name;
    private int price;
    private int stock;
}
