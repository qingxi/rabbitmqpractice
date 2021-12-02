package RabbitMQDemo.routing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import RabbitMQDemo.Constants;
import RabbitMQDemo.RabbitMQHelper;
//Routing（Exchange为direct，组播）
/*
 *    			  ---routing key 1----|||||----C1
 * p ---|exchange|---routing key 2----|||||----C2
 */
public class PubBus {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, TimeoutException {

		ArrayList<String> inputs = new ArrayList<String>();

		for (int i = 0; i < 10; i++) {
			inputs.add(new Scanner(System.in).next());
		}

		Connection conn = RabbitMQHelper.GetConnection();
		final Channel channel = conn.createChannel();
		String routingKey ="1";
		for (int i=0;i<inputs.size();i++) {
			routingKey = (i%2==0?"1":"2");
			System.out.println(routingKey);
			channel.basicPublish(Constants.EXCHANGE_WEATHER_ROUTING, routingKey, null, inputs.get(i).getBytes());
		}
		channel.close();
		conn.close();
	}
}
