package com.netframe.test;

import com.netframe.test.model.Event;
import com.netframe.test.repository.EventRepository;
import com.netframe.test.service.SaveScoreService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableJpaRepositories("com.netframe.test.repository") 
@EntityScan("com.netframe.test.model")
@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class TestNetFrameProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestNetFrameProjectApplication.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    
    
 
    
    //@Bean
    public CommandLineRunner run(EventRepository eventrRepository) throws Exception {
        return (String[] args) -> {
            
            
            Event event1 = new Event("team1", "team2");
            Event event2 = new Event("team2", "team3");

            eventrRepository.save(event1);
            eventrRepository.save(event2);
            eventrRepository.findAll().forEach(event -> System.out.println(event));
        };
    }

}
