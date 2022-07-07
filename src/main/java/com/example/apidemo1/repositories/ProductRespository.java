package com.example.apidemo1.repositories;

import com.example.apidemo1.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRespository extends JpaRepository<Product, Long> {

    List<Product> findByProductNameEquals(String productName);

}
