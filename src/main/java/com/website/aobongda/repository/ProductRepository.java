package com.website.aobongda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.website.aobongda.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM Product as p WHERE p.name LIKE ?1")
	Product findByName(String name);
//	@Query("SELECT p FROM Product as p WHERE p.club.id_club = ?1")
//	Product findByIdClub(Long id);
}
