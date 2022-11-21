package com.website.aobongda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.service.impl.IVoucherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VoucherController {
	@Autowired
	private final IVoucherService iVoucherService;

	@GetMapping("/vouchers")
	public ResponseEntity<?> getAllProducts() {
		return ResponseEntity.ok(iVoucherService.getAllVouchers());
	}

	@GetMapping("/voucher/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(iVoucherService.getVoucherById(id));
	}
}
