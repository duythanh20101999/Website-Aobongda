package com.website.aobongda.service.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.website.aobongda.dto.ClubDTO;
import com.website.aobongda.exception.AppException;
import com.website.aobongda.model.Brand;
import com.website.aobongda.model.Club;
import com.website.aobongda.model.League;
import com.website.aobongda.model.Product;
import com.website.aobongda.payload.response.ClubResponse;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.repository.BrandRepository;
import com.website.aobongda.repository.ClubRepository;
import com.website.aobongda.repository.LeagueRepository;
import com.website.aobongda.service.impl.IClubService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService implements IClubService {
	private final ClubRepository clubRepository;
	private final BrandRepository brandRepository;
	private final LeagueRepository leagueRepository;
	private final ModelMapper modelMapper;

	@Override
	public Club findByID(Long id) {
		Optional<Club> club = clubRepository.findById(id);
		return club.orElse(null);
	}

	@Override
	public List<Club> findAll() {
		return clubRepository.findAll();
	}

	@Override
	public Club save(ClubDTO clubDTO) {

		boolean check = leagueRepository.existsById(clubDTO.getLeagueId())
				&& brandRepository.existsById(clubDTO.getBrandId());
		if (check) {
			Club club = new Club();
//			club.setId(clubDTO.getId());
//			club.setNameClub(clubDTO.getNameClub());
			club = modelMapper.map(clubDTO, Club.class);
			League league = leagueRepository.getReferenceById(clubDTO.getLeagueId());
			club.setLeague(league);
			Brand brand = brandRepository.getReferenceById(clubDTO.getBrandId());
			club.setBrand(brand);
			return clubRepository.save(club);
		} else {
			throw new AppException(404, "Brand or League not exist.");
		}

	}

	@Override
	public Club updateClub(ClubDTO clubDTO) {
		Club clubUpdate = findByID(clubDTO.getId());

		if (clubUpdate != null) {
			clubUpdate.setName(clubDTO.getName());
			return clubRepository.save(clubUpdate);
		} else
			throw new AppException(404, "Club ID not found");
	}

	@Override
	public Boolean deleteClub(Long clubId) {

		Club clubDelete = findByID(clubId);
		if (clubDelete != null) {
			clubRepository.deleteById(clubId);
			return true;
		} else
			return false;
	}

	@Override
	public DataResponse<ClubResponse> createClub(ClubDTO newClub) {
		DataResponse<ClubResponse> response = new DataResponse<>();

		Club club = modelMapper.map(newClub, Club.class);
        //Brand brand = brandRepository.getById(newClub.getBrandId());
		Brand brand = brandRepository.getReferenceById(newClub.getBrandId());
        League league = leagueRepository.getReferenceById(newClub.getLeagueId());
		club.setBrand(brand);
		club.setLeague(league);
		clubRepository.save(club);
		ClubResponse clubResponse = modelMapper.map(club, ClubResponse.class);
		response.setSuccess(true);
		response.setMessage("Create Success");
		response.setData(clubResponse);
		return response;
	}

}
