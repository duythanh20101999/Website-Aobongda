package com.website.aobongda.repository;

import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.website.aobongda.model.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
	/*
	 * @Query("select c from Club c where c.parentClub is null ") List<Club>
	 * findAllParentClub();
	 */

	/*
	 * @Query("select c from Club c where c.parentClub.id =:id") List<Club>
	 * findAllSubcateByCate(Long id);
	 */
}
