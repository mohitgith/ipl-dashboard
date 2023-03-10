package com.learning.ipldashboard.repository;

import org.springframework.data.repository.CrudRepository;

import com.learning.ipldashboard.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long>{
    
    Team findByTeamName(String teamName);
}
