package com.website.aobongda.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class test {
	@GetMapping("/test")
	public String test() {
		return "ok";
	}
}
