package com.travelplatform.ai.controller;

import com.travelplatform.ai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
       this.chatClient = chatClient;
    }


    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient
                .prompt()
                .system("""
                        You are a travel concierge assistant. Your role is to assist 
                        travelers with travel planning, destination information, 
                        booking recommendations, and travel-related inquiries.
                        If a user requests help with anything outside of these 
                        responsibilities, respond politely and inform them that you are 
                        only able to assist with travel planning and booking tasks.
                        """)
                .user(message)
                .call().content();
    }

}
