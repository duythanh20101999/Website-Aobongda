package com.website.aobongda.service.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.model.Club;

import com.website.aobongda.model.Product;
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
	// private final OrderDetailRepository orderDetailRepo;
	private final ProductImageService productImageService;

	@Override
	public Product saveNewProduct(ProductReq productReq) {
		Product product = new Product();
		Club club = clubRepo.getReferenceById(productReq.getClubID());
		// OrderDetail orderDetail =
		// orderDetailRepo.getReferenceById(productReq.getOrderDetailId());
		product = mapper.map(productReq, Product.class);
		product.setClub(club);
		productRepo.save(product);
		Product finalProduct = product;
		if (productReq.getImages() != null) {

			productReq.getImages().forEach(ProductImageReq -> {
				ProductImageReq.setProductID(finalProduct.getId());
			});
			productImageService.saveNewImage(productReq.getImages());
		}
		return product;
	}
}
