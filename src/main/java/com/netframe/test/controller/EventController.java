package com.netframe.test.controller;

import com.netframe.test.model.Event;
import com.netframe.test.repository.EventRepository;
import com.netframe.test.service.GameService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author liubov
 */
@Controller
public class EventController {
    
    @Autowired
    private GameService gameService;
    
    @GetMapping("/")
    public String watchGamePage(Model model) {

        Iterable<Event> event = gameService.findAll();
        
        model.addAttribute("games", event);
        return "games";
    }
    
    @GetMapping("/clear")
    public String clearGames() {
        try {
            gameService.deleteAll();
        } catch (Exception ex) {
            
        }
        
        return "redirect:/";
    }
  
}
