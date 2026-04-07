package com.example.slot_access.web;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
            "service", "slot_access",
            "status", "running",
            "hint", "Use /api/admin/** endpoints with Basic Auth"
        );
    }
}
