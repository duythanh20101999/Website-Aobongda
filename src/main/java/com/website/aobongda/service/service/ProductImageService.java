package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.website.aobongda.dto.ProductImageReq;
import com.website.aobongda.model.Product;
import com.website.aobongda.model.ProductImage;
import com.website.aobongda.repository.ProductImageRepository;
import com.website.aobongda.repository.ProductRepository;
import com.website.aobongda.service.impl.IProductImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService {
	private final ProductImageRepository productImageRepo;
	private final ProductRepository productRepo;

	@Override
	public boolean saveNewImage(List<ProductImageReq> productImageReqs) {
		List<ProductImage> productImages = new ArrayList<>();
		productImageReqs.forEach(ProductImageReq -> {
			Product product = productRepo.getReferenceById(ProductImageReq.getProductID());
			productImages.add(new ProductImage(null, ProductImageReq.getUrlImage(), product));
		});
		return productImageRepo.saveAll(productImages).size() > 0;
	}
}
