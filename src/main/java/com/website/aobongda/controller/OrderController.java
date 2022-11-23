package com.website.aobongda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.OrderReq;
import com.website.aobongda.service.impl.IOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {
	@Autowired
	private final IOrderService iorderService;

	@PostMapping("/order")
	private ResponseEntity<?> create(@RequestBody OrderReq orderReq) {
		//Order order = iorderService.create(orderReq);
		//return ResponseEntity.ok(new ResponseDTO(true, "Success", order));
		return ResponseEntity.ok(iorderService.create(orderReq));
	}
	
	@GetMapping("/admin/orders")
	public ResponseEntity<?> getAllOrder() {
		return ResponseEntity.ok(iorderService.getAllOrders());
	}
	
	@GetMapping({"/user/order/{id}", "/admin/order/{id}"})
	public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(iorderService.getOrderById(id));
	}

}
