package com.website.aobongda.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.aobongda.dto.ProductDetailResp;
import com.website.aobongda.dto.ProductImageResp;
import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.model.Club;

import com.website.aobongda.model.Product;
import com.website.aobongda.model.ProductImage;
import com.website.aobongda.repository.ClubRepository;

import com.website.aobongda.repository.ProductRepository;
import com.website.aobongda.service.impl.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService implements IProductService {

	private final ProductRepository productRepo;
	private final ModelMapper mapper;
	private final ClubRepository clubRepo;

	private final ProductImageService productImageService;

	@Override
	public Product saveNewProduct(ProductReq productReq) {
		Product product = new Product();
		Club club = clubRepo.getReferenceById(productReq.getClubID());
		product = mapper.map(productReq, Product.class);
		product.setClub(club);
		productRepo.save(product);
		Product finalProduct = product;
		if (productReq.getImages() != null) {

			productReq.getImages().forEach(image -> {
				image.setProductID(finalProduct.getId());
			});
			productImageService.saveNewImage(productReq.getImages());
			
		}
		
		return product;
	}

	@Override
	public ProductDetailResp findProductByID(Long productId) {
		Optional<Product> productOp = productRepo.findById(productId);
		Product product = productOp.orElse(null);
		if (product == null)
			return null;
		List<ProductImage> productImages = product.getProductimage();
		ProductDetailResp productResp = mapper.map(product, ProductDetailResp.class);
		List<ProductImageResp> productImageResps = new ArrayList<>();
		productImages.forEach(ProductImage -> {
			ProductImageResp productImageResp = mapper.map(ProductImage, ProductImageResp.class);
			productImageResps.add(productImageResp);
		});
		productResp.setImages(productImageResps);
		return productResp;
	}
}
