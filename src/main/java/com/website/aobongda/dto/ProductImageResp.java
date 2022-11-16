package com.website.aobongda.dto;

import java.util.List;

import com.website.aobongda.model.ProductImage;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductImageResp {
	List<ProductImage> images;
}
