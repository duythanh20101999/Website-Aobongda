package com.website.aobongda.service.impl;

import com.website.aobongda.dto.OrderReq;
import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.model.Order;
import com.website.aobongda.payload.response.DataResponse;

public interface IOrderService {
	DataResponse<OrderReq> create(OrderReq orderReq);
}
