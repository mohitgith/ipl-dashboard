package com.learning.ipldashboard.repository;

import org.springframework.data.repository.CrudRepository;

import com.learning.ipldashboard.model.Match;

public interface MatchRepository extends CrudRepository<Match, Long> {
    
    
}
