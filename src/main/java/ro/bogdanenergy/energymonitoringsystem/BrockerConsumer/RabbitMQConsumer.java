package ro.bogdanenergy.energymonitoringsystem.BrockerConsumer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import ro.bogdanenergy.energymonitoringsystem.dto.RabbitmqMeasurementDTO;
import ro.bogdanenergy.energymonitoringsystem.service.MeasurementService;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component @Slf4j
public class RabbitMQConsumer {

    @Autowired
    private MeasurementService measurementService;


    @RabbitListener(queues = "measurement_queue")
    public void receivedMessage(byte[] bytes) {
        String s = new String(bytes, StandardCharsets.UTF_8);
        JSONObject jo = new JSONObject(s);
        RabbitmqMeasurementDTO measurementDTO = new RabbitmqMeasurementDTO(jo.getInt("id"), jo.getDouble("measurement"), jo.getLong("timestamp"));
        log.info("New measurement received from RabbitMQ: {}", measurementDTO);
        measurementService.createMeasurementForRabbitMqDTO(measurementDTO);
    }
}