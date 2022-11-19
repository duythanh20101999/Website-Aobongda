package com.website.aobongda.service.impl;

import com.website.aobongda.dto.ProductDetailResp;
import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.model.Product;

public interface IProductService {
	Product saveNewProduct(ProductReq productReq);
	ProductDetailResp findProductByID(Long productId);
	
}
