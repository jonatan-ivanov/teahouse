package org.example.teahouse.tea.controller;

import java.util.concurrent.CompletableFuture;

import org.example.teahouse.tea.observation.SseObservationHandler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class SseController {
    private final SseObservationHandler sseObservationHandler;

    SseController(SseObservationHandler sseObservationHandler) {
        this.sseObservationHandler = sseObservationHandler;
    }

    @GetMapping(path="/sse", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    SseEmitter sse() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        CompletableFuture.runAsync(() -> emit(sseEmitter));
        return sseEmitter;
    }

    @SuppressWarnings({"InfiniteLoopStatement"})
    private void emit(SseEmitter sseEmitter) {
        try {
            while (true) {
                sseEmitter.send(sseObservationHandler.take());
            }
        }
        catch (Exception e) {
            sseEmitter.completeWithError(e);
        }
    }
}
