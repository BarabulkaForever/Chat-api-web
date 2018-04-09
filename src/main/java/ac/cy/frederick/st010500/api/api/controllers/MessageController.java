package ac.cy.frederick.st010500.api.api.controllers;

import ac.cy.frederick.st010500.api.api.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    public void message(Message message) {
        simpMessagingTemplate.convertAndSend(String.format("/topic/%s", message.getChannel()),
                new Message(message.getName(), message.getMessage()));
    }

}