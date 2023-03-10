package com.learning.ipldashboard.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.learning.ipldashboard.model.Team;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final EntityManager entityManager;

  @Autowired
  public JobCompletionNotificationListener(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

      // to store the data of individual team
      Map<String, Team> teamData = new HashMap<>();

      entityManager.createQuery("select distinct m.team1, count(*) from Match m group by m.team1", Object[].class) // Specifying the type as Object[], as the data from the query will be of both String and number
      .getResultList()
      .stream()
      .map(e -> new Team((String) e[0], (long) e[1])) // After streaming the data (Object[]), maping the value as Team instance as the Object[] is of String and number
      .forEach(team -> teamData.put(team.getTeamName(), team)); // For each entry, putting the value in the map

      entityManager.createQuery("select distinct m.team2, count(*) from Match m group by m.team2", Object[].class)
      .getResultList()
      .stream()
      .forEach(e -> {
        Team team = teamData.get((String) e[0]);
        team.setTotalMatches(team.getTotalMatches() + (long) e[1]);
      }); // For each entry, putting the value in the map

      entityManager.createQuery("select distinct m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
      .getResultList()
      .stream()
      .forEach(e -> {
        Team team = teamData.get((String) e[0]);
        if (team != null) 
          team.setTotalWins((long) e[1]);
      }); // For each entry, putting the value in the map

      teamData.values().forEach(team -> entityManager.persist(team));
      teamData.values().forEach(team -> System.out.println(team));

    }
  }

  @Override
  public void beforeJob(JobExecution arg0) {
    // throw new UnsupportedOperationException("Unimplemented method 'beforeJob'");
    System.out.println("Before Job is executed.");
  }
}