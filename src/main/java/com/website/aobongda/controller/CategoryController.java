//package com.website.aobongda.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.nhom13.dto.CategoryDTO;
//import com.nhom13.service.impl.ICategoryService;
//
//@RestController
//@RequestMapping("/api")
//public class CategoryController {
//	@Autowired
//	ICategoryService categoryService;
//	
//	@GetMapping("/category")
//	public ResponseEntity<?> getAllCategories(){
//		return ResponseEntity.ok(categoryService.getAllCategories());
//	}
//	
//	@GetMapping("/category/{id}")
//	public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){
//		return ResponseEntity.ok(categoryService.getCategoryById(id));
//	}
//	
//	@PostMapping("/admin/insert_category")
//	public ResponseEntity<?> insert(@RequestBody CategoryDTO categoryDTO){
//		return ResponseEntity.ok(categoryService.insert(categoryDTO));
//	}
//	
//	@PutMapping("/admin/update_category/{id}")
//	public ResponseEntity<?> update(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long id){
//		return ResponseEntity.ok(categoryService.update(categoryDTO, id));
//	}
//}
