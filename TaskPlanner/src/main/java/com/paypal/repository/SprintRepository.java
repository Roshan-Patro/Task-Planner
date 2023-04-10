package com.paypal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paypal.model.Sprint;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer> {
	@Query("select s from Sprint s where s.creatorId=:creatorId")
	public List<Sprint> getSprintsByCreatorId(Integer creatorId);
}
