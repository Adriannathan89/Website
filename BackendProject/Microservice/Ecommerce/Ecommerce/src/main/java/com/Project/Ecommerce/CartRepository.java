package com.Project.Ecommerce;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

}
