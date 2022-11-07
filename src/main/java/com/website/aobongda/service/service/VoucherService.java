package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.website.aobongda.dto.VoucherReq;
import com.website.aobongda.model.Order;
import com.website.aobongda.model.Voucher;
import com.website.aobongda.repository.OrderRepository;
import com.website.aobongda.repository.VoucherRepository;
import com.website.aobongda.service.impl.IVoucherService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor

public class VoucherService implements IVoucherService {
	private final VoucherRepository voucherRepo;
	private final OrderRepository orderRepo;

	@Override
	public Voucher save(VoucherReq voucherReq) {
		List<Voucher> checkList = voucherRepo.findByCode(voucherReq.getCode());
		if (checkList.size() > 0) {
			return null;
		}

		String code = voucherReq.getCode();
		Long price = voucherReq.getPrice();
		int status = voucherReq.getStatus();
		Order order = orderRepo.getReferenceById(voucherReq.getOrderId());
//		List<Order> orderList = orderList(order);

		Voucher voucher = new Voucher();
		voucher.setCode(code);
		voucher.setPrice(price);
		voucher.setStatus(status);
		voucher.setOrder(order);

		return voucherRepo.save(voucher);
	}

	@Override
	public List<Voucher> get(String code) {
		return voucherRepo.findByCode(code);
	};

	@Override
	public void delete(String code) {
		voucherRepo.deleteVoucherByCode(code);
	};

	@Override
	public Voucher update(VoucherReq voucherReq) {
		Long id = voucherReq.getId();
		String code = voucherReq.getCode();
		Long price = voucherReq.getPrice();
		int status = voucherReq.getStatus();
		Order order = orderRepo.getReferenceById(voucherReq.getOrderId());

		Voucher updatedVoucher = voucherRepo.findById(id).get();

		if (updatedVoucher != null) {

			if (code == null && code.trim().equals(""))
				updatedVoucher.setCode(code.trim().replaceAll("  ", " "));
			else
				updatedVoucher.setCode(code);
			if (price == null)
				updatedVoucher.setPrice(0l);
			else
				updatedVoucher.setPrice(price);

			updatedVoucher.setStatus(status);

			updatedVoucher.setOrder(order);

			return voucherRepo.save(updatedVoucher);

		} else
			return null;
	};

	@Override
	public List<Voucher> getByOrder(Long orderId) {
		List<Voucher> allVouchers = voucherRepo.findAll();
		List<Voucher> returnVouchers = new ArrayList<>();
		for (Voucher currentVoucher : allVouchers) {
			for (Order order : orderRepo.getOrderById(currentVoucher.getOrder())) {
				if (order.getId() == orderId) {
					returnVouchers.add(currentVoucher);
				}
			}
		}
		return returnVouchers;
	};

	@Override
	public Voucher addOrderToVoucher(Long voucherId, Long orderId) {
		Order order = orderRepo.findById(orderId).get();

		if (order == null) {
			return null;
		}
		Voucher updatedVoucher = voucherRepo.findById(voucherId).get();

		if (updatedVoucher == null) {
			return null;
		}

		/* Check if the voucher already contains the order */
		Boolean existed = false;
		for (Order item : orderRepo.getOrderById(updatedVoucher.getOrder())) {
			if (item.getId() == orderId) {
				existed = true;
			}
		}
		/* If not in the list */
		if (existed == false) {
			updatedVoucher.setOrder(order);
		}

		return voucherRepo.save(updatedVoucher);
	};
	/*
	 * private List<Order> orderList(List<Long> order_id_list) { List<Order>
	 * orderList = new ArrayList<>(); for (Long id : order_id_list) { try { Order
	 * order = orderRepo.findById(id).get(); if (order != null) {
	 * orderList.add(order); } } catch (Exception e) {
	 * 
	 * }
	 * 
	 * } return orderList; }
	 */
}