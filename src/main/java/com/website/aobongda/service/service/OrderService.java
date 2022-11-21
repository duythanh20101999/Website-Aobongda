package com.website.aobongda.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.aobongda.dto.OrderReq;
import com.website.aobongda.model.Cart;
import com.website.aobongda.model.Order;
import com.website.aobongda.model.Product;
import com.website.aobongda.model.User;
import com.website.aobongda.model.Voucher;
import com.website.aobongda.repository.CartRepository;
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
	private Long price = (long) 0;

	@Override
	public Order create(OrderReq orderReq) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Long id = user.getId();
		User users = userRepository.getReferenceById(id);
		Order order = new Order();
		order.setName(users.getName());
		order.setPhone(users.getPhone());
		order.setAddress(orderReq.getAddress());
		order.setNote(orderReq.getNote());
		order.setCode(orderReq.getCode());

		/*
		 * Voucher voucher = voucherRepository.findByCode(orderReq.getCode());
		 * if(orderReq.getCode()== voucher.getCode()) {
		 * order.setPriceOff(voucher.getPrice()); } else
		 * {order.setPriceOff(Long.valueOf(0));}
		 */
		order.setPriceOff(Long.valueOf(20000));
		order.setPriceShip(Long.valueOf(100000));
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
		return order;
	}
}
