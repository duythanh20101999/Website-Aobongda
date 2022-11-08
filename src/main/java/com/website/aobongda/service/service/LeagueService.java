package com.website.aobongda.service.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.website.aobongda.exception.NotFoundException;
import com.website.aobongda.model.League;
import com.website.aobongda.repository.LeagueRepository;
import com.website.aobongda.service.impl.ILeagueService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LeagueService implements ILeagueService{
	private final LeagueRepository leagueRepo;

	@Override
    public League saveNewLeague(League league) {
        return leagueRepo.save(league);
    }

    @Override
    public League updateLeague(Long id, League league) {
        Optional<League> optionalLeague = leagueRepo.findById(id);
        if (!optionalLeague.isPresent()) {
            throw new NotFoundException("League not found");
        }
        if (leagueRepo.findByName(league.getName()) != null) {
            throw new IllegalArgumentException("Name already exist");
        }
        League updateLeague = optionalLeague.get();
        updateLeague.setName(league.getName());
        return leagueRepo.save(updateLeague);
    }	

    @Override
    public void deleteLeague(Long id) {
        leagueRepo.deleteById(id);
    }

    @Override
    public List<League> getAllLeague(Integer page, Integer size) {
        List<League> Leagues = leagueRepo.findAll();
        PagedListHolder<League> pagedListHolder = new PagedListHolder<>(Leagues);
        pagedListHolder.setPage(page - 1);
        pagedListHolder.setPageSize(size);
        return pagedListHolder.getPageList();
    }
}