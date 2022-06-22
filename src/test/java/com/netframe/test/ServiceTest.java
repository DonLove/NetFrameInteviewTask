
package com.netframe.test;

import com.netframe.test.model.Event;
import com.netframe.test.model.Score;
import com.netframe.test.repository.EventRepository;
import com.netframe.test.repository.ScoreRepository;
import com.netframe.test.service.ScoreService;
import com.netframe.test.service.ITemplateEngine;
import com.netframe.test.service.SaveScoreService;
import com.netframe.test.service.SseService;
import com.netframe.test.service.TemplateEngineWrapper;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.util.concurrent.ForkJoinPool.commonPool;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer.Random;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 *
 * @author liubov
 */
@SpringBootTest
public class ServiceTest {
    
    @Mock
    private EventRepository eventRepository;
    
    @Mock
    private ScoreRepository scoreRepository;

    //private ITemplateEngine itemplateEngine;
    @Mock
    SpringTemplateEngine templateEngine;
    
    @Mock
    SaveScoreService scoreService;
    
    @Mock
    ScoreService gameService;
    
    @Mock
    private SseService sseService;
    
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    
    @BeforeEach
    void initUseCase() {
        templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setOrder(1);
        resolver.setCacheable(true);
        templateEngine.addTemplateResolver(resolver);
        TemplateEngineWrapper wrapper = new TemplateEngineWrapper(templateEngine);
        scoreService = new SaveScoreService(eventRepository, scoreRepository);
        
    }
    
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
       
    }
    
    //@Test
    void saveDuplicateEvent () {
        Event createdEvent = new Event("team50", "team60");
        int firstScore = (int)(Math.random() * 10);
        int secondScore = (int)(Math.random() * 10);
        createdEvent.setScore(new Score(firstScore, secondScore));
        
        when(eventRepository.save(any(Event.class))).thenReturn(createdEvent);
        when(eventRepository.findByTeam1AndTeam2(any(String.class), any(String.class))).thenReturn(Optional.empty());
        ITemplateEngine mock = mock(ITemplateEngine.class);
        when(mock.process(any(String.class), any(Context.class))).thenReturn("");
        when(sseService.send(anyString())).thenReturn(Boolean.TRUE);
       
        final CountDownLatch countDown = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(2);
        IntStream.range(0, 5).forEach((i) ->
        service.submit(() -> {
            System.out.println("Wait all threads" + countDown.getCount());
            try {
                countDown.await();
            } catch (InterruptedException ex) {}
            System.out.println("start pool to save");
            boolean result = gameService.saveEvent(createdEvent);
            System.out.println("result = " + result);
        }));
        
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        countDown.countDown();
        
        try {
            service.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            
        }
        
    }
    
    @Test
    void saveEvent () {
        Event createdEvent = new Event("team50", "team60");
        int firstScore = (int)(Math.random() * 10);
        int secondScore = (int)(Math.random() * 10);
        createdEvent.setScore(new Score(firstScore, secondScore));
        
        when(eventRepository.save(any(Event.class))).thenReturn(createdEvent);
        when(eventRepository.findByTeam1AndTeam2(any(String.class), any(String.class))).thenReturn(Optional.empty());

       
        
        boolean result = scoreService.saveEvent(createdEvent);
        assertEquals(true, result);
        
    }
    
    @Test
    void saveScore () {
        Event createdEvent = new Event("team50", "team60");
        int firstScore = (int)(Math.random() * 10);
        int secondScore = (int)(Math.random() * 10);
        createdEvent.setScore(new Score(firstScore, secondScore));
        Score old = new Score(firstScore - 1, secondScore);
        createdEvent.setId(1L);
        
        when(scoreRepository.findById(any(Long.class))).thenReturn(Optional.of(old));
        when(scoreRepository.save(any(Score.class))).thenReturn(old);
        when(eventRepository.findByTeam1AndTeam2(any(String.class), any(String.class))).thenReturn(Optional.of(createdEvent));
       
        
        boolean result = scoreService.saveEvent(createdEvent);
        assertEquals(true, result);
    }
}
