/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.netframe.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netframe.test.model.Event;
import com.netframe.test.model.Score;
import com.netframe.test.service.SaveScoreService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author liubov
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ControllerTest {
    
    @Mock
    SaveScoreService scoreService;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    
    private String URI = "/score";
    
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void send() throws Exception {
        Event createdEvent = new Event("team5", "team6");
        int firstScore = (int)(Math.random() * 10);
        int secondScore = (int)(Math.random() * 10);
        createdEvent.setScore(new Score(firstScore, secondScore));
        
        when(scoreService.saveEvent(any(Event.class))).thenReturn(true);

        System.out.println("content  : = " + mapper.writeValueAsString(createdEvent));
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createdEvent).getBytes(StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String userResp = result.getResponse().getContentAsString();
        
        System.out.println("userResp : = " + userResp);
        Assertions.assertThat(userResp).isNotEmpty();
        //Assertions.assertThat(userJson).isEqualToIgnoringCase(mapper.writeValueAsString(v));

    }
    
    @Test
    public void sendUpdate() throws Exception {
        Event createdEvent = new Event("team5", "team6");
        int firstScore = (int)(Math.random() * 10);
        int secondScore = (int)(Math.random() * 10);
        createdEvent.setScore(new Score(firstScore, secondScore));
        createdEvent.setId(1l);
        
        when(scoreService.saveEvent(any(Event.class))).thenReturn(true);

        System.out.println("content  : = " + mapper.writeValueAsString(createdEvent));
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createdEvent).getBytes(StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String userResp = result.getResponse().getContentAsString();
        
        System.out.println("userResp : = " + userResp);
        Assertions.assertThat(userResp).isNotEmpty();
        //Assertions.assertThat(userJson).isEqualToIgnoringCase(mapper.writeValueAsString(v));

    }
}
