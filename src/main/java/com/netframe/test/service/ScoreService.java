
package com.netframe.test.service;

import com.netframe.test.model.Event;
import com.netframe.test.model.Score;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 *
 * @author liubov
 */
@Service
public class ScoreService {
    
    @Autowired
    private SseService sseService;
    
    
    @Autowired
    SpringTemplateEngine templateEngine;
    
    
    @Autowired
    SaveScoreService saveGameScrvice;
    
    private final ConcurrentSkipListSet<String> savingKeys = new ConcurrentSkipListSet<>();

    public ScoreService() {
    }
    
    public void setTemplateEngine(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void setSseService(SseService sseService) {
        this.sseService = sseService;
    }
    
    
    public boolean saveEvent(Event newEvent) {
        if(newEvent == null) {
            return false;
        }
        String key = newEvent.getTeam1()+newEvent.getTeam2();

        while(savingKeys.contains(key)) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ex) {
                
            }
        }
        savingKeys.add(key);
        
        if (saveGameScrvice.saveEvent(newEvent)) {
            updateEvents(newEvent);
        }  
        savingKeys.remove(key);
      
        return true;
    }
    
 
    public boolean updateEvents(Event event) {
        Context myContext = new Context();
        myContext.setVariable("game", event);
        String result = "{\"game\":\"" + templateEngine.process("rowModel", myContext) + "\", \"id\":\"" + event.getId() + "\"}\n\n";
        sseService.send(result);
        return true;
    }
    
    
    public SseEmitter subscribe() {
        
        return sseService.subscribe();

    }
    
}
