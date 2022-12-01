package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.aobongda.dto.OrderReq;
import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.model.Cart;
import com.website.aobongda.model.Order;
import com.website.aobongda.model.OrderDetail;
import com.website.aobongda.model.Payment;
import com.website.aobongda.model.Product;
import com.website.aobongda.model.User;
import com.website.aobongda.model.Voucher;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.OrderResponse;
import com.website.aobongda.repository.CartRepository;
import com.website.aobongda.repository.OrderRepository;
import com.website.aobongda.repository.PaymentRepository;
import com.website.aobongda.repository.ProductRepository;
import com.website.aobongda.repository.UserRepository;
import com.website.aobongda.repository.VoucherRepository;
import com.website.aobongda.security.userprincipal.UserPrincipal;
import com.website.aobongda.service.impl.IOrderService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements IOrderService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	PaymentRepository paymentRepository;
	private Long price = (long) 0;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public DataResponse<OrderReq> create(OrderReq orderReq) {
		DataResponse<OrderReq> response = new DataResponse<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Long id = user.getId();
		User users = userRepository.getReferenceById(id);
		Order order = new Order();
		// order.setId(orderReq.getId());
		order.setName(users.getName());
		order.setPhone(users.getPhone());
		order.setAddress(orderReq.getAddress());
		order.setNote(orderReq.getNote());
		order.setCode(orderReq.getCode());

		Voucher voucher = voucherRepository.findByCode(orderReq.getCode());

		order.setUser(users);
		order.setVoucher(voucher);
		Payment payment = paymentRepository.getReferenceById(orderReq.getId_payment());
		order.setPayment(payment);

		if (orderReq.getCode().equals(voucher.getCode())) {
			order.setPriceOff(voucher.getPrice());
		} else {
			order.setPriceOff(Long.valueOf(0));
		}

		order.setPriceShip(Long.valueOf(10000));
		order.setStatus(orderReq.getStatus());
		List<Cart> cartList = cartRepository.findByCartID_UserId(id);

		if (cartList != null) {
			cartList.forEach(cart -> {

				Product product = productRepository.getReferenceById(cart.getCartID().getProductId());
				price += (cart.getQuantity() * product.getPrice());
			});
		}
		order.setTotalPriceOrigin(price);
		Long totalPrice = price + order.getPriceShip() - order.getPriceOff();
		order.setTotalPrice(totalPrice);
		orderRepository.save(order);
		response.setSuccess(true);
		response.setMessage("Create successful order");
		response.setData(orderReq);
		return response;
	}

	
	@Override
	public DataResponse<OrderResponse> getAllOrders() {
		DataResponse<OrderResponse> response = new DataResponse<>();
		List<Order> orders = orderRepository.findAll();
		if(orders != null) {
			List<OrderResponse> listOrder = new ArrayList<>();
			for(Order order : orders) {
				OrderResponse orderResponse = new OrderResponse();
				orderResponse = modelMapper.map(order, OrderResponse.class);
				orderResponse.setUsername(order.getUser().getUsername());
				orderResponse.setStatus(order.getStatus());
				
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
		Order order = orderRepository.getById(id);
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
				orderResponse.setStatus(order.getStatus());
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
