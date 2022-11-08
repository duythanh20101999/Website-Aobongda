package com.website.aobongda.service.impl;

import java.util.List;

import com.website.aobongda.model.League;

public interface ILeagueService {

	League saveNewLeague(League league);

	League updateLeague(final Long id, League league);

	void deleteLeague(final Long id);

	List<League> getAllLeague(Integer page, Integer size);
}

