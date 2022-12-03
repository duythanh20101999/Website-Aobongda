package com.website.aobongda.service.impl;

import com.website.aobongda.dto.OrderReq;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.OrderResponse;

public interface IOrderService {
	DataResponse<?> create(OrderReq orderReq);
	DataResponse<OrderResponse> getAllOrders();
	DataResponse<OrderResponse> getOrderById(Long id);
}
