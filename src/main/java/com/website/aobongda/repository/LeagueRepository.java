package com.website.aobongda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.aobongda.model.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

}
