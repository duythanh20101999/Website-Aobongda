package com.website.aobongda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.service.impl.IOrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderController {
	@Autowired
	IOrderService service;
	
	@GetMapping("/admin/orders")
	public ResponseEntity<?> getAllOrder() {
		return ResponseEntity.ok(service.getAllOrders());
	}
	
	@GetMapping({"/user/order/{id}", "/admin/order/{id}"})
	public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.getOrderById(id));
	}

}
