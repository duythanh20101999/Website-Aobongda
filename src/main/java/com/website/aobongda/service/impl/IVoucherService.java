package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.dto.VoucherReq;
import com.website.aobongda.model.Voucher;

public interface IVoucherService {
	Voucher save(VoucherReq voucherReq);
	List<Voucher> get(String code);
	void delete(String code);
	Voucher update(VoucherReq voucherReq);
	List<Voucher> getByOrder(Long orderId);
	Voucher addOrderToVoucher(Long voucherId, Long orderId);
}
