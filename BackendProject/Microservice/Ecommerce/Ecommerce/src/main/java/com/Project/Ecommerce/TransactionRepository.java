package com.Project.Ecommerce;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Transaction findByUser(User user);
}
