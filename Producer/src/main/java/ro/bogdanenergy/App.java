package ro.bogdanenergy;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.SerializationUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class App 
{
    public static void main( String[] args )
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("measurement_queue", false, false, false, null);
            BufferedReader br = new BufferedReader(new FileReader("sensor.csv"));
            String line;
            int id = 3;
            while ((line = br.readLine()) != null) {
                Measurement measurement = new Measurement(id, Double.parseDouble(line));
                JSONObject jo = new JSONObject(measurement);
                channel.basicPublish("", "measurement_queue", null, jo.toString().getBytes());
          }
//            String message = "blablabla";
  //          channel.basicPublish("", "measurement_queue", null, message.getBytes());
            channel.close();
            connection.close();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
