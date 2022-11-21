package com.website.aobongda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.OrderReq;
import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.model.Order;
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
		Order order = iorderService.create(orderReq);
		return ResponseEntity.ok(new ResponseDTO(true, "Success", order));
	}
}
