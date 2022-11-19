package com.website.aobongda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.mapper.LeagueMapper;
import com.website.aobongda.model.League;
import com.website.aobongda.service.service.LeagueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/league")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class LeagueController {
	private final LeagueService leagueService;
	private final LeagueMapper leagueMapper; 

	@PostMapping
	private ResponseEntity<?> saveLeague(@RequestBody League league) {
		League newLeague = leagueService.saveNewLeague(league);
		if (newLeague != null) {
			return ResponseEntity.ok(new ResponseDTO(true, "Success", newLeague));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, "Failed", null));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateLeague(@PathVariable final Long id, @RequestBody League league) {
		try {
			// Update ok
			return ResponseEntity
					.ok(new ResponseDTO(true, "Success", leagueMapper.toLeagueDTO(leagueService.updateLeague(id, league))));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, "Failed", e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteLeague(@PathVariable final Long id) {
		try {
			// Delete ok
			leagueService.deleteLeague(id);
			return ResponseEntity.ok(new ResponseDTO(true, "Success", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, "Failed", e.getMessage()));
		}
	}

	@GetMapping
	public ResponseEntity<?> getAllShop(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "4") Integer size) {
		try {
			// Get all ok
			return ResponseEntity
					.ok(new ResponseDTO(true, "Success", leagueMapper.toLeagueDTO(leagueService.getAllLeague(page, size))));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(false, "Failed", e.getMessage()));
		}
	}
}
