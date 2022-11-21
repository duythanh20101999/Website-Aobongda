package com.website.aobongda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CartResp {
	private int quantiy;
	private Items item;

	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public static class Items {
		String productName;
		Long price;
		String image;
	}
}
