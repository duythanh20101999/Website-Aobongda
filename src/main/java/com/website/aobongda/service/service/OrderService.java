package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.model.Order;
import com.website.aobongda.model.OrderDetail;
import com.website.aobongda.model.User;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.OrderResponse;
import com.website.aobongda.repository.OrderRepository;
import com.website.aobongda.security.userprincipal.UserPrincipal;
import com.website.aobongda.service.impl.IOrderService;


@Service
public class OrderService implements IOrderService{
	
	@Autowired
	OrderRepository repository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public DataResponse<OrderResponse> getAllOrders() {
		DataResponse<OrderResponse> response = new DataResponse<>();
		List<Order> orders = repository.findAll();
		if(orders != null) {
			List<OrderResponse> listOrder = new ArrayList<>();
			for(Order order : orders) {
				OrderResponse orderResponse = new OrderResponse();
				orderResponse = modelMapper.map(order, OrderResponse.class);
				orderResponse.setUsername(order.getUser().getUsername());
				if(order.getStatus() == 0) {
					orderResponse.setStatus("Chưa thanh toán");
				}else {
					orderResponse.setStatus("Đã thanh toán");
				}
				
				List<OrderDetail> orDetails = order.getOrderDetails();
				List<ProductReq> listProduct = new ArrayList<>();
				for(OrderDetail orderDetail: orDetails) {
					ProductReq product = modelMapper.map(orderDetail.getProduct(), ProductReq.class);
					listProduct.add(product);
				}
				
				orderResponse.setProducts(listProduct);
				listOrder.add(orderResponse);
			}
			response.setSuccess(true);
			response.setMessage("Success");
			response.setDatas(listOrder);
			
		}else {
			response.setSuccess(false);
			response.setMessage("Order is empty");
		}
		return response;
	}

	@Override
	public DataResponse<OrderResponse> getOrderById(Long id) {
		DataResponse<OrderResponse> response = new DataResponse<>();
		Order order = repository.getById(id);
		if(order != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
			String us = order.getUser().getUsername();
			Long idtest = user.getId();
			if(user.getUsername().equals(order.getUser().getUsername()) 
					|| user.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN")) {
				OrderResponse orderResponse = new OrderResponse();
				orderResponse = modelMapper.map(order, OrderResponse.class);
				orderResponse.setUsername(order.getUser().getUsername());
				if(order.getStatus() == 0) {
					orderResponse.setStatus("Chưa thanh toán");
				}else {
					orderResponse.setStatus("Đã thanh toán");
				}
				List<OrderDetail> orDetails = order.getOrderDetails();
				List<ProductReq> listProduct = new ArrayList<>();
				for(OrderDetail orderDetail: orDetails) {
					ProductReq product = modelMapper.map(orderDetail.getProduct(), ProductReq.class);
					listProduct.add(product);
				}
				orderResponse.setProducts(listProduct);
				response.setSuccess(true);
				response.setMessage("Success");
				response.setData(orderResponse);
			}else {
				response.setSuccess(false);
				response.setMessage("You do not have access");
			}
		}else {
			response.setSuccess(false);
			response.setMessage("Order not found");
		}
		return response;
	}
	
}
