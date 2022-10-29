package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.dto.ClubDTO;
import com.website.aobongda.model.Club;

public interface IClubService {
	Club findByID(Long id);
	List<Club> findAll();
	Club save(ClubDTO clubDTO);
	Club updateClub(ClubDTO clubDTO);
	Boolean deleteClub(Long clubId);
}
