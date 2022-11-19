package com.website.aobongda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.ProductDetailResp;
import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.model.Product;
import com.website.aobongda.service.impl.IProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {
	@Autowired
	private final IProductService iproductService;

	@PostMapping("/admin/create_product")
	private ResponseEntity<?> create(@RequestBody ProductReq productReq) {
		return ResponseEntity.ok(iproductService.create(productReq));
	}
	
	@GetMapping("/products")
	public ResponseEntity<?> getAllProducts(){
		return ResponseEntity.ok(iproductService.getAllProducts());
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
		return ResponseEntity.ok(iproductService.getProductById(id));
	}
	
	@PutMapping("/admin/update_product/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ProductReq request){
		return ResponseEntity.ok(iproductService.update(id, request));
	}
}
