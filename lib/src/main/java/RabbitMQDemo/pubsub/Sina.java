package RabbitMQDemo.pubsub;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import RabbitMQDemo.Constants;
import RabbitMQDemo.RabbitMQHelper;

public class Sina {
	public static void main(String[] args) throws IOException,TimeoutException{
		Connection conn = RabbitMQHelper.GetConnection();
		final Channel channel = conn.createChannel();
		channel.queueDeclare(Constants.QUEUE_SINA, false, false, false, null);
		channel.queueBind(Constants.QUEUE_SINA, Constants.EXCHANGE_WEATHER_LAZY, "");
		
		channel.basicQos(1);
		channel.basicConsume(Constants.QUEUE_SINA,false,new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				// TODO Auto-generated method stub
				System.out.println("Sina-"+ new String(body));
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
			
			
		});
	}
}
