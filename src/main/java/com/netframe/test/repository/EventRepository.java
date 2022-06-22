package com.netframe.test.repository;

import com.netframe.test.model.Event;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author liubov
 */

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    
        Optional<Event>  findByTeam1AndTeam2(String team1, String team2);
   
}
