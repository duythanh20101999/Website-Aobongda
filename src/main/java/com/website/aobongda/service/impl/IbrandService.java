package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.model.Brand;

public interface IbrandService {
    Brand saveNewBrand(Brand brand);
    Brand updateBrand(final Long id, Brand brand);

    void deleteBrand(final Long id);
    
    List<Brand> getAllBrand(Integer page, Integer size);
}
