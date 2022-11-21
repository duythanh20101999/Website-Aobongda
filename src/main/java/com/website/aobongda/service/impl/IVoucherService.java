package com.website.aobongda.service.impl;

import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.VoucherResponse;


public interface IVoucherService {
	DataResponse<VoucherResponse> getAllVouchers();
	DataResponse<VoucherResponse> getVoucherById(Long id);
}
