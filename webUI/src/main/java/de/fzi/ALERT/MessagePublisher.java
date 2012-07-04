package de.fzi.ALERT;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;


public class MessagePublisher {
	String topic = "WebUIServlet";
//	private String url = "tcp://kalmar17.fzi.de:61616";
	private String url = "tcp://localhost:61616";
	private Connection connection;
	private Session session;
	private MessageProducer publisher;
	
	TextMessage message;
	
	MessagePublisher(){
		
	}
	
	MessagePublisher(String topic, String url){
		this.topic = topic;
		this.url = url;
	}
	
	public void publisch(String msg) throws Exception{
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
		connection = factory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topic = session.createTopic(this.topic);
		publisher = session.createProducer(topic);
		publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		message = this.session.createTextMessage(msg);
		
		connection.start();
		
		publisher.send(message);
		
		connection.stop();
		connection.close();
		
		System.out.println("A message is successful send to ActiveMQ");
	}

}
