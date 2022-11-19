package com.website.aobongda.payload.response;

import com.website.aobongda.dto.ClubDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductReponse {
	String name, description, image;
	Long price;
	int status;
	ClubDTO club;
}
