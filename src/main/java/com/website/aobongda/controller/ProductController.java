package com.website.aobongda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.model.Product;
import com.website.aobongda.service.impl.IProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
	private final IProductService iproductService;
    @PostMapping("/product")
    private ResponseEntity<?> addProduct(@RequestBody ProductReq productReq){
        Product product= iproductService.saveNewProduct(productReq);
        if (product !=null){
            return ResponseEntity.ok(new ResponseDTO(true,"Success",product));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(false,"Failed",null));
    }
}
