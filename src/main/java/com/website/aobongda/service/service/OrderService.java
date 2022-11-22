package com.website.aobongda.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.aobongda.dto.OrderReq;
import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.model.Cart;
import com.website.aobongda.model.Club;
import com.website.aobongda.model.Order;
import com.website.aobongda.model.Payment;
import com.website.aobongda.model.Product;
import com.website.aobongda.model.User;
import com.website.aobongda.model.Voucher;
import com.website.aobongda.payload.response.DataResponse;
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
		orderRepository.save(order);
		response.setSuccess(true);
		response.setMessage("Create successful product");
		response.setData(orderReq);
		return response;

		// orderRepository.save(order);
		// return order;
	}
}
