package com.website.aobongda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.ProductImageReq;
import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.service.impl.IProductImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductImageController {
	private final IProductImageService iproductImageService;

	@PostMapping("/productImageUrl")
	private ResponseEntity<?> addImageProduct(@RequestBody List<ProductImageReq> productImageReqs) {
		if (productImageReqs.size() != 0) {
			boolean check = iproductImageService.saveNewImage(productImageReqs);
			if (check) {
				return ResponseEntity.ok(new ResponseDTO(true, "Success", null));
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, "Failed", null));

	}
}
