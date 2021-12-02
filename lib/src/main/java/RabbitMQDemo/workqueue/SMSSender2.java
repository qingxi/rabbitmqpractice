package RabbitMQDemo.workqueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import RabbitMQDemo.Constants;
import RabbitMQDemo.RabbitMQHelper;

public class SMSSender2 {
	public static void main(String[] args) throws IOException,TimeoutException{		
		Connection conn = RabbitMQHelper.GetConnection();
		final Channel channel1 = conn.createChannel();
		
		//create queue
		//queue name,persistence,private,auto delete after disconnection,other params.
		channel1.queueDeclare(Constants.QUEUE_SMS, false, false, false, null);
		
		channel1.basicQos(1);//每次处理一个
		
		channel1.basicConsume(Constants.QUEUE_SMS,false,new DefaultConsumer(channel1) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				
				String jsonSMS = new String(body);
				System.out.println("SMSSender2"+jsonSMS);
				
				try {
					Thread.sleep(30);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				channel1.basicAck(envelope.getDeliveryTag(), false);
			}						
		});
		
	}	
}
