package com.website.aobongda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.aobongda.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
