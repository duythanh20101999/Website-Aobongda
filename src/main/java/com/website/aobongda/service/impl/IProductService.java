package com.website.aobongda.service.impl;

import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.ProductReponse;

public interface IProductService {
	
	DataResponse<ProductReq> create (ProductReq request);
	DataResponse<?> update (Long id, ProductReq request);
	DataResponse<ProductReponse> getAllProducts();
	DataResponse<ProductReponse> getProductById(Long id);
	
}
