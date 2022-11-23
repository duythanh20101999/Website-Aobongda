package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.dto.ClubDTO;
import com.website.aobongda.model.Club;
import com.website.aobongda.payload.response.ClubResponse;
import com.website.aobongda.payload.response.DataResponse;

public interface IClubService {
	Club findByID(Long id);
	List<Club> findAll();
	Club save(ClubDTO clubDTO);
	Club updateClub(ClubDTO clubDTO);
	Boolean deleteClub(Long clubId);
	DataResponse<ClubResponse> createClub(ClubDTO newClub);
}
