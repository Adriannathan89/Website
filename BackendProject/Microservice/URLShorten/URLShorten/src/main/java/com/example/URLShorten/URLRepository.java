package com.example.URLShorten;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends CrudRepository<URL, Long> {
    URL findByname(String ShortURL);
}