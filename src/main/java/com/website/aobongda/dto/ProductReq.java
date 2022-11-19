package com.website.aobongda.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductReq {
	private Long id;
	private String name;
	private String description;
	private Long price;
	private int status;
	private Long clubID;
	private List<ProductImageReq> images;
}
