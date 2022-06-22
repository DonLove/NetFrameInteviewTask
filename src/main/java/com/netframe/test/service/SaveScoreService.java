
package com.netframe.test.service;


import com.netframe.test.model.Event;
import com.netframe.test.model.Score;
import com.netframe.test.repository.EventRepository;
import com.netframe.test.repository.ScoreRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author liubov
 */
@Service
public class SaveScoreService {

    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private ScoreRepository scoreRepository;
    
    
    
    public SaveScoreService() {
    }

    
    public SaveScoreService(EventRepository eventRepository, ScoreRepository scoreRepository) {
        this.eventRepository = eventRepository;
        this.scoreRepository = scoreRepository;
        
    }

    
    @Transactional (rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public boolean saveEvent(Event newEvent) {
        
      
        Optional<Event> e = eventRepository.findByTeam1AndTeam2(newEvent.getTeam1(), newEvent.getTeam2());
        
        if(!e.isPresent()) {
            eventRepository.save(newEvent);          
            return true;
            
            
        } else {
            
            Long id = e.get().getId();
            Score score = scoreRepository.findById(id).orElseThrow();

            boolean needUpdate = score.getTeam1Score() < newEvent.getScore().getTeam1Score() || 
                                    score.getTeam2Score() < newEvent.getScore().getTeam2Score();
            score.setTeam1Score(Math.max(score.getTeam1Score(), newEvent.getScore().getTeam1Score() ));
            score.setTeam2Score(Math.max(score.getTeam2Score(), newEvent.getScore().getTeam2Score() ));
            newEvent.setId(id);
            newEvent.setScore(new Score(score.getTeam1Score(), score.getTeam2Score()));
            
            scoreRepository.save(score);
            
            if(needUpdate)
               return true;
        }
        return false;
    }
    
    

    
}
