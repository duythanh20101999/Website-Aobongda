package com.website.aobongda.service.impl;

import com.website.aobongda.dto.OrderReq;
import com.website.aobongda.model.Order;

public interface IOrderService {
	Order create(OrderReq orderReq);
}
