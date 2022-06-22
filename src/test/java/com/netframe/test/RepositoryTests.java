package com.netframe.test;

import com.netframe.test.model.Event;
import com.netframe.test.model.Score;
import com.netframe.test.repository.EventRepository;
import com.netframe.test.repository.ScoreRepository;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
class RepositoryTests {

    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private ScoreRepository scoreRepository;
    
   
    

    @Test
    void testSaveEvent() {
        List<Event> events = Arrays.asList(
                new Event("team0", "team1"),
                new Event("team2", "team3"),
                new Event("team3", "team4")
        );
        events.forEach(event -> event.setScore(new Score((int)(Math.random() * 10), (int)(Math.random() * 10))));
        Iterable<Event> allCustomer = eventRepository.saveAll(events);
        AtomicInteger valid = new AtomicInteger();
        events.forEach(event -> {
            if(event.getId() > 0){
                valid.getAndIncrement();
            }
        });
        
        assertThat(valid.intValue()).isEqualTo(3);
        
    }
    
    @Test
    void testUniqEvent() {
        Event dulicateEvent = new Event("team05", "team06");
        //Score s = new Score((int)(Math.random() * 10), (int)(Math.random() * 10));
        //dulicateEvent.setScore(s);
        
        Event duplicate = eventRepository.findByTeam1AndTeam2(dulicateEvent.getTeam1(), dulicateEvent.getTeam2()).orElse(null);
        
        if(duplicate != null) {
            Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
                Event d = eventRepository.save(dulicateEvent);
            
                });
        }
        
        
        
    }
    
    @Test
    void testNullScoreEvent() {
        Event nullScoreEvent = new Event("team1", "team2");
        eventRepository.save(nullScoreEvent);
        assertNotNull(nullScoreEvent.getId());
        
        Event event= eventRepository.findById(nullScoreEvent.getId()).orElse(null);
             assertNotNull(event.getScore());
             assertEquals(0, event.getScore().getTeam1Score());
             assertEquals(0, event.getScore().getTeam2Score());
        
    }
    
    @Test
    void findAll_success() {
        List<Event> events = (List<Event>) eventRepository.findAll();
        assertThat(events.size()).isGreaterThanOrEqualTo(1);
    }
    
    @Test
    void testGetScore() {
        Event createdEvent = new Event("team05", "team06");
        int firstScore = (int)(Math.random() * 10);
        int secondScore = (int)(Math.random() * 10);
        createdEvent.setScore(new Score(firstScore, secondScore));
        eventRepository.save(createdEvent);
            assertNotNull(createdEvent.getId());
        
        Event event= eventRepository.findById(createdEvent.getId()).orElse(createdEvent);
            assertNotNull(event.getScore());
            assertEquals(firstScore, event.getScore().getTeam1Score());
            assertEquals(secondScore, event.getScore().getTeam2Score());
        
        Score score = scoreRepository.findById(createdEvent.getId()).orElse(null);
            assertNotNull(score);
            assertEquals(firstScore, score.getTeam1Score());
            assertEquals(secondScore, score.getTeam2Score());
        
          //  testUniqEvent();
    }

}
