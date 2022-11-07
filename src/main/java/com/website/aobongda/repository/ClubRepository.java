package com.website.aobongda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.aobongda.model.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long>{

}
