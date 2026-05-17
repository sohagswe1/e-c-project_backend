package com.example.sj.Ai_bot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AI_Service {
    @Autowired
    private ChatClient chatClient;
    

    public String getresponse (String userinput) {
        String response = chatClient.prompt().user(userinput)
                .call().content();
                
        return response;
        
    }
    
}
