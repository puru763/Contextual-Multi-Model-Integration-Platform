package com.travelplatform.ai.controller;

import com.travelplatform.ai.tools.TravelBookingTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/travel")
public class TravelBookingController {

    private final ChatClient chatClient;
    private final TravelBookingTools travelBookingTools;

    public TravelBookingController(@Qualifier("travelBookingChatClient") ChatClient chatClient,
            TravelBookingTools travelBookingTools) {
        this.chatClient = chatClient;
        this.travelBookingTools = travelBookingTools;
    }

    @GetMapping("/booking")
    public ResponseEntity<String> travelBooking(@RequestHeader("username") String username,
            @RequestParam("message") String message) {
        String answer = chatClient.prompt()
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(message)
                .tools(travelBookingTools)
                .toolContext(Map.of("username", username))
                .call().content();
        return ResponseEntity.ok(answer);
    }
}
