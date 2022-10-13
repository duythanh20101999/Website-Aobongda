package com.website.aobongda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.website.aobongda.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
	Boolean findByName(String name);
}
