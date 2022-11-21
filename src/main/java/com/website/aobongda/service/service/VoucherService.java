package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.website.aobongda.model.Voucher;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.VoucherResponse;
import com.website.aobongda.repository.VoucherRepository;
import com.website.aobongda.service.impl.IVoucherService;

@Service
public class VoucherService implements IVoucherService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	VoucherRepository repository;

	@Override
	public DataResponse<VoucherResponse> getAllVouchers() {
		DataResponse<VoucherResponse> response = new DataResponse<>();
		List<Voucher> vouchers = repository.findAll();
		List<VoucherResponse> listVoucher = new ArrayList<>();
		for (Voucher voucher : vouchers) {
			VoucherResponse VoucherResponse = modelMapper.map(voucher, VoucherResponse.class);
			listVoucher.add(VoucherResponse);
		}
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setDatas(listVoucher);
		return response;
	}

	@Override
	public DataResponse<VoucherResponse> getVoucherById(Long id) {
		DataResponse<VoucherResponse> response = new DataResponse<>();
		Voucher voucher = repository.getById(id);
		if (voucher == null) {
			response.setSuccess(false);
			response.setMessage("Voucher not found");
			return response;
		}
		VoucherResponse VoucherResponse = modelMapper.map(voucher, VoucherResponse.class);
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setData(VoucherResponse);
		return response;
	}

}
