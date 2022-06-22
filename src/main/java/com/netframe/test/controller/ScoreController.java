
package com.netframe.test.controller;

import com.netframe.test.model.Event;
import com.netframe.test.service.ScoreService;
import com.netframe.test.service.SaveScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * @author liubov
 */

@RestController
public class ScoreController {
    
    @Autowired
    private ScoreService gameService;

    public ScoreController(ScoreService gameService) {
        this.gameService = gameService;
    }
    

    @PostMapping(value = "/score", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> setScore(@RequestBody Event newEvent) {
        
        if(gameService.saveEvent(newEvent)) {
            return new ResponseEntity<>("Score saved", HttpStatus.OK);
        }
        
        return new ResponseEntity<>("Key dublicated", HttpStatus.OK);
        
    }
    
    
    @RequestMapping("/subscription")
    public SseEmitter subscribe() {
       
        SseEmitter emitter = gameService.subscribe();

        return emitter;
    }
}
