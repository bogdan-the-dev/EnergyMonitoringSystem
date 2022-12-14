package ro.bogdanenergy.energymonitoringsystem.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.bogdanenergy.energymonitoringsystem.dto.WebSocketMessageDTO;

@Data
@Slf4j
@Service
public class NotificationService {

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(WebSocketMessageDTO messageDTO) {
        messagingTemplate.convertAndSend("/chat/info",messageDTO);
        log.info("websocket message sent");
    }
}
