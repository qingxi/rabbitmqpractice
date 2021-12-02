package RabbitMQDemo.basic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import RabbitMQDemo.Constants;

public class Cosummer {

	public static void main(String[] args) throws IOException,TimeoutException {
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
		//false:代表手动编程来确认消息。（true）
		channel1.basicConsume(Constants.QUEUE_HELLOWORLD,false,new Reciver(channel1));
	}
}

class Reciver extends DefaultConsumer{

	private Channel channel;
	public Reciver(Channel channel) {
		super(channel);
		this.channel = channel;
	}
	
	@Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
        throws IOException
    {
		String message = new String(body);
		System.out.print(message);
		
		System.out.print("Tag id: "+ envelope.getDeliveryTag());
		channel.basicAck(envelope.getDeliveryTag(), false);
    }	
}