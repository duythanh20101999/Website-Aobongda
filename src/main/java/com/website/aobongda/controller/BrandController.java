package com.website.aobongda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.mapper.BrandMapper;
import com.website.aobongda.model.Brand;
import com.website.aobongda.service.service.BrandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor

public class BrandController {
	private final BrandService brandService;
	private final BrandMapper brandMapper;
	@PostMapping
	private ResponseEntity<?> saveBrand(@RequestBody Brand brand) {
		Brand newBrand = brandService.saveNewBrand(brand);
		if (newBrand != null) {
			return ResponseEntity.ok(new ResponseDTO(true, "Success", newBrand));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, "Failed", null));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateBrand(@PathVariable final Long id, @RequestBody Brand brand) {
		try {
			// Update ok
			return ResponseEntity
					.ok(new ResponseDTO(true, "Success", brandMapper.toBrandDTO(brandService.updateBrand(id, brand))));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, "Failed", e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBrand(@PathVariable final Long id) {
		try {
			// Delete ok
			brandService.deleteBrand(id);
			return ResponseEntity.ok(new ResponseDTO(true, "Success", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, "Failed", e.getMessage()));
		}
	}

	@GetMapping
	public ResponseEntity<?> getAllShop(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "4") Integer size) {
		try {
			// Get all ok
			return ResponseEntity
					.ok(new ResponseDTO(true, "Success", brandMapper.toBrandDTO(brandService.getAllBrand(page, size))));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, "Failed", e.getMessage()));
		}
	}
}
