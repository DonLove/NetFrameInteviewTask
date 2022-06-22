package com.netframe.test.service;

import com.netframe.test.model.Event;
import com.netframe.test.repository.EventRepository;
import com.netframe.test.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author liubov
 */

@Service
public class GameService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ScoreRepository scoreRepository;
    
    
    public Iterable<Event> findAll() {
        return eventRepository.findAll();
    }
    
    @Transactional (rollbackFor = Exception.class)
    public void deleteAll() throws Exception {
        eventRepository.findAll().forEach((e) -> {
            scoreRepository.deleteById(e.getId());
            eventRepository.deleteById(e.getId());
        });

    }
}
