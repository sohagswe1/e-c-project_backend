package com.example.sj.Ai_bot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class Ai_contoller {

    @Autowired
    private AI_Service aiService;
    @GetMapping("/getresponse")
    public ResponseEntity<String> getresponse(@RequestParam String userinput) {
        return ResponseEntity.ok(aiService.getresponse(userinput));
    }
    
}
