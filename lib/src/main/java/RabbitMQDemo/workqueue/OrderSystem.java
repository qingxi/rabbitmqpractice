package RabbitMQDemo.workqueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.rabbitmq.client.*;

import RabbitMQDemo.Constants;
import RabbitMQDemo.RabbitMQHelper;
//工作队列模式（no exchange
/*
 *    		|||||----C1
 * p -------|||||----C2
 */
public class OrderSystem {

	public static void main(String[] args) throws IOException,TimeoutException{
		
		Connection conn = RabbitMQHelper.GetConnection();
		Channel channel1 = conn.createChannel();
		
		//create queue
		//queue name,persistence,private,auto delete after disconnection,other params.
		channel1.queueDeclare(Constants.QUEUE_SMS, false, false, false, null);
		
		for(int i=0;i<100;i++) {
			SMS sms =new SMS("乘客"+i, "13900000","您的车票已预定成功！");
			String jsonSMS = new Gson().toJson(sms);
			channel1.basicPublish("",Constants.QUEUE_SMS,null,jsonSMS.getBytes());
		}
		System.out.print("Send successfully");
		channel1.close();
		conn.close();
	}
}
