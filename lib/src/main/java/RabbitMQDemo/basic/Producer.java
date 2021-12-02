package RabbitMQDemo.basic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import RabbitMQDemo.Constants;

public class Producer {

	public static void main(String[] args) throws IOException, TimeoutException {
		// TODO Auto-generated method stub
		System.out.print("Hello world!");
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection conn = connectionFactory.newConnection();
		Channel channel1 = conn.createChannel();
		
		//create queue
		//queue name,persistence,private,auto delete after disconnection,other params.
		channel1.queueDeclare(Constants.QUEUE_HELLOWORLD, false, false, false, null);
		
		String message ="This is william 3.";
		channel1.basicPublish("",Constants.QUEUE_HELLOWORLD,null,message.getBytes());
		channel1.close();
		conn.close();
		System.out.print("send successfully.");
		
	}
}
