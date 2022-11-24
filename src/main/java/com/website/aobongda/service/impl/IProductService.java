package com.website.aobongda.service.impl;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.ProductReponse;

public interface IProductService {
	
	DataResponse<ProductReq> create (ProductReq request, MultipartFile image) throws IOException;
	DataResponse<?> update (Long id, ProductReq request, MultipartFile image) throws IOException;
	DataResponse<ProductReponse> getAllProducts();
	DataResponse<ProductReponse> getProductById(Long id);
	
}
