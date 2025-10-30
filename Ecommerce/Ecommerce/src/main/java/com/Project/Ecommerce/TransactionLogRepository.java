package com.Project.Ecommerce;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogRepository extends CrudRepository<TransactionLog, Long> {
    TransactionLog findByDays(int days);
}
