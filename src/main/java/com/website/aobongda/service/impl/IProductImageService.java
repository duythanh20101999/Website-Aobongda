package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.dto.ProductImageReq;

public interface IProductImageService {
	boolean saveNewImage(List<ProductImageReq> productImageReqs);
}
