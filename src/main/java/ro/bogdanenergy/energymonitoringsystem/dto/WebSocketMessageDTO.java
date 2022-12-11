package ro.bogdanenergy.energymonitoringsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class WebSocketMessageDTO {

    private Integer deviceOwnerId;

    private Integer deviceId;

    private String message;
}
