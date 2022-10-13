package com.website.aobongda.service.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.website.aobongda.exception.NotFoundException;
import com.website.aobongda.model.Brand;
import com.website.aobongda.repository.BrandRepository;
import com.website.aobongda.service.impl.IbrandService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService implements IbrandService{
	private final BrandRepository brandRepo;

	@Override
    public Brand saveNewBrand(Brand Brand) {
        return brandRepo.save(Brand);
    }

    @Override
    public Brand updateBrand(Long id, Brand Brand) {
        Optional<Brand> optionalBrand = brandRepo.findById(id);
        if (!optionalBrand.isPresent()) {
            throw new NotFoundException("Brand not found");
        }
        if (brandRepo.findByName(Brand.getName()) != null) {
            throw new IllegalArgumentException("Name already exist");
        }
        Brand updateBrand = optionalBrand.get();
        updateBrand.setName(Brand.getName());
        return brandRepo.save(updateBrand);
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepo.deleteById(id);
    }

    @Override
    public List<Brand> getAllBrand(Integer page, Integer size) {
        List<Brand> Brands = brandRepo.findAll();
        PagedListHolder<Brand> pagedListHolder = new PagedListHolder<>(Brands);
        pagedListHolder.setPage(page - 1);
        pagedListHolder.setPageSize(size);
        return pagedListHolder.getPageList();
    }
}
