package inflearn.interview.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @GetMapping("/health-check")
    public ResponseEntity<Void> checkHealthStatus() {
        return ResponseEntity.ok().build();
    }
}
