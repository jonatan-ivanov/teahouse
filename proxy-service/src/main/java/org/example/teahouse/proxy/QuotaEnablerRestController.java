package org.example.teahouse.proxy;

import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuotaEnablerRestController {

    private final SimpleRateLimiter simpleRateLimiter;

    public QuotaEnablerRestController(SimpleRateLimiter simpleRateLimiter) {
        this.simpleRateLimiter = simpleRateLimiter;
    }

    @PostMapping("/quota")
    Mono<Void> enableQuota(@RequestBody Map<String, String> body) {
        return Mono.defer(() -> {
            this.simpleRateLimiter.setEnabled(Boolean.parseBoolean(body.get("enabled")));
            return Mono.empty();
        });
    }
}
