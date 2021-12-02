package RabbitMQDemo.pubsub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import RabbitMQDemo.Constants;
import RabbitMQDemo.RabbitMQHelper;
//发布订阅模式（exchagne为 fanout，广播）
/*
 *    			  -------|||||----C1
 * p ---|exchange|-------|||||----C2
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
		//演示队列，用rabbitMQ来实现。发送的时候需要加入property 参数
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("x-delayed-type", "fanout");
		channel.exchangeDeclare(Constants.EXCHANGE_WEATHER_LAZY,"x-delayed-message",true,false,params);

		Map<String,Object> headers = new HashMap<String,Object>();
		headers.put("x-delay", 60000);
		AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder().headers(headers);
		
		for (String input : inputs) {
			channel.basicPublish(Constants.EXCHANGE_WEATHER_LAZY, "", props.build(), input.getBytes());
		}
		channel.close();
		conn.close();
	}
}
