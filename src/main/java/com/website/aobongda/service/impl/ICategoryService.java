package com.website.aobongda.service.impl;

import com.website.aobongda.dto.CategoryDTO;
import com.website.aobongda.payload.response.DataResponse;

public interface ICategoryService {

	DataResponse<CategoryDTO> getAllCategories();
	DataResponse<CategoryDTO> getCategoryById(Long id);
	DataResponse<?> insert(CategoryDTO category);
	DataResponse<?> update(CategoryDTO category, Long id);
}
