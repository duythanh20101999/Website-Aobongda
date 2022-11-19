package com.website.aobongda.service.service;

import java.security.cert.CertStoreSpi;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.aobongda.dto.ClubDTO;
import com.website.aobongda.dto.ProductReq;
import com.website.aobongda.model.Club;
import com.website.aobongda.model.Product;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.payload.response.ProductReponse;
import com.website.aobongda.repository.ClubRepository;
import com.website.aobongda.repository.ProductRepository;
import com.website.aobongda.service.impl.IProductService;

@Service
public class ProductService implements IProductService {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ProductRepository repository;
	@Autowired
	ClubRepository clubRepository;

	@Override
	public DataResponse<ProductReq> create(ProductReq request) {
		DataResponse<ProductReq> response = new DataResponse<>();
		Club club = clubRepository.getById(request.getId_club());
		Product product = modelMapper.map(request, Product.class);
		product.setClub(club);
		repository.save(product);
		response.setSuccess(true);
		response.setMessage("Create successful product");
		response.setData(request);
		return response;
	}

	@Override
	public DataResponse<?> update(Long id, ProductReq request) {
		DataResponse<?> response = new DataResponse<>();
		Product product = repository.getById(id);
		if (product == null) {
			response.setSuccess(false);
			response.setMessage("Product not found");
			return response;
		}
		product = modelMapper.map(request, Product.class);
		product.setId(id);
		product.setClub(clubRepository.getById(request.getId_club()));
		repository.save(product);
		response.setSuccess(true);
		response.setMessage("Update successful");
		return response;
	}

	@Override
	public DataResponse<ProductReponse> getAllProducts() {
		DataResponse<ProductReponse> response = new DataResponse<>();
		List<Product> products = repository.findAll();
		List<ProductReponse> listProduct = new ArrayList<>();
		for (Product product : products) {
			ProductReponse productReponse = modelMapper.map(product, ProductReponse.class);
			productReponse.setClub(modelMapper.map(product.getClub(), ClubDTO.class));
			listProduct.add(productReponse);
		}
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setDatas(listProduct);
		return response;
	}

	@Override
	public DataResponse<ProductReponse> getProductById(Long id) {
		DataResponse<ProductReponse> response = new DataResponse<>();
		Product product = repository.getById(id);
		if (product == null) {
			response.setSuccess(false);
			response.setMessage("Product not found");
			return response;
		}
		ProductReponse productReponse = modelMapper.map(product, ProductReponse.class);
		productReponse.setClub(modelMapper.map(product.getClub(), ClubDTO.class));
		response.setSuccess(true);
		response.setMessage("Ok");
		response.setData(productReponse);
		return response;
	}

}
