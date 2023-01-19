package ro.bogdanenergy;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;


public class App 
{
    public static void main( String[] args ) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("measurement_queue", true, false, false, null);
            BufferedReader br = new BufferedReader(new FileReader("sensor.csv"));
            LocalDateTime date = LocalDateTime.now();
            String line;
            int id = 38802;
            int i = 0;
            while ((line = br.readLine()) != null && i < 100) {

                i++;
                Measurement measurement = new Measurement(id, Double.parseDouble(line), date);
                JSONObject jo = new JSONObject(measurement);
                channel.basicPublish("", "measurement_queue", null, jo.toString().getBytes());
                date = date.plusMinutes(10);
            }
            channel.close();
            connection.close();
        }
        catch(IOException | TimeoutException e){
                e.printStackTrace();
            }
        }
    }
