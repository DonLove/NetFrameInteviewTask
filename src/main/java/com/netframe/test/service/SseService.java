
package com.netframe.test.service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * @author liubov
 */
@Service
public class SseService {

    
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public boolean add(SseEmitter sseEmitter) {
        return this.emitters.add(sseEmitter);
    }

    public boolean remove(SseEmitter sseEmitter) {
        return this.emitters.remove(sseEmitter);
    }

    public List<SseEmitter> getSsEmitters() {
        return this.emitters;
    }
    
    public SseEmitter subscribe() {
       
        SseEmitter emitter = new SseEmitter(24 * 60 * 60 * 1000l);

        this.add(emitter);
        emitter.onCompletion(() -> {
            this.remove(emitter);
        });

        
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; true; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                      .data("SSE MVC - " + LocalTime.now().toString())
                      .id(String.valueOf(i))
                      .name("ping");
                    emitter.send(event);
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (IOException | InterruptedException ex) {
                emitter.completeWithError(ex);
            }
        });
    
    
        return emitter;
    }
    
    public boolean send(Object result) {
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            this.getSsEmitters().forEach((SseEmitter emitter) -> {
                try {
                    
                    emitter.send(result);

                } catch (IOException e) {
                    emitter.complete();
                    this.remove(emitter);
                    //e.printStackTrace();
                }
            });
        });
        return true;
    }

}

