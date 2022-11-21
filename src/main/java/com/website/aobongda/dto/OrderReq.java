package com.website.aobongda.dto;

import lombok.Data;

@Data
public class OrderReq {

	private String name;
	private String phone;
	private String address;
	private String note;
	private Long totalPriceOrigin;
	private Long priceOff;
	private Long priceShip;
	private Long totalPrice;
	private String status;
	private String code;
}
