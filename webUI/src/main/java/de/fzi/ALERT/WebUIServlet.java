package de.fzi.ALERT;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class WebUIServlet extends HttpServlet implements MessageListener{
	MessagePublisher messagePublisher;
	Connection connection;
	String activeMQUser;
	String activeMQPass;
	
	private String url = "tcp://localhost:61616";
	private String publisherTopic = "WebUIServlet";
	private String subscribeTopic = "WebUIServlet";
	
	
	String username;
	String email;
	String uuid = null;
	int waitTime = 10;
	
	
	public WebUIServlet(){

	}
	
	public void init(ServletConfig servletConfig){
		System.out.println("WebUIServlet is starting!");
		
		 this.url = servletConfig.getInitParameter("activeMQ");
		 this.publisherTopic = servletConfig.getInitParameter("indentityVerifyRequestTopic");
		 this.subscribeTopic = servletConfig.getInitParameter("indentityVerifyResponseTopic");
		 this.waitTime = Integer.parseInt(servletConfig.getInitParameter("timeout"));
		// init the ActiveMQ Connection
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				activeMQUser, activeMQPass, url);
		Session session = null;
		MessageConsumer consumer = null;

		try {
			connection = connectionFactory.createConnection();

			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Topic bTopic = session.createTopic(subscribeTopic);
			consumer = session.createConsumer(bTopic);
			consumer.setMessageListener(this);

		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("Failed to bild a connection for broker");
		}
		
		messagePublisher = new MessagePublisher(publisherTopic, url);
		System.out.println("Message Publisher is successful created!");
		
		
	}
	 
	public void doGet(HttpServletRequest req, HttpServletResponse res)
             throws ServletException, IOException {

		//start the connection of ActiveMQ
		try {
			connection.start();
			System.out.println("The broker started!");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		username = req.getParameter("username");
		email = req.getParameter("email");
		
		String message = XMLMessageParser.createVertifyRequest(username, email);
//		System.out.println("create a message:" + message);
				
//		out.println("<HTML>");
////		out.println("<HEAD><TITLE>Hello, " + name + "</TITLE></HEAD>");
//		out.println("<BODY>");
////		out.println("Hello, " + name);
//		out.println("<IMG src = \"img/loading.gif\">");
//		out.println("</BODY></HTML>");
		
		
		try {
			messagePublisher.publisch(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final CountDownLatch countDownLatch = new CountDownLatch(2);
		countDownLatch.countDown();

		Thread thread = new Thread(){
			public void run(){
				int i=0;
				try{
					while(i<waitTime){
						if(uuid==null){
							i++;
							System.out.println(i);
							Thread.sleep(1000);
						} else {
							System.out.println("Get a responce from server");
//							res.sendRedirect("main.jsp");
							break;
						}
					}
					
//					res.sendRedirect("index.jsp");
				} catch(InterruptedException e) {
					System.out.println("Thread sleep exception!");
				} finally {                    
					countDownLatch.countDown();                
				}
			}
		};
		
		thread.start();		
		
		try {            
			countDownLatch.await(waitTime, TimeUnit.SECONDS);        
		} catch (InterruptedException e) {            
			System.out.println("Count down latch interrupted ");        
		}
		
		if(uuid != null){
			String redirectURL = "main.jsp?username=" + username + "&email=" + email
					+ "&uuid=" + uuid;
			res.sendRedirect(redirectURL);
		} else {
			res.sendRedirect("index.jsp");
			System.out.println("Time out!");
		}
		
		//stop the connection of ActiveMQ
		try {
			connection.stop();
			System.out.println("The broker stoped!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getServletInfo() {
		return "A servlet that knows the name of the person to whom it's" + 
				"saying hello";
	}

	public void onMessage(Message message) {
		System.out.println("On Message is starting!");
		if(message instanceof TextMessage){
			try {
				TextMessage textMessage = (TextMessage)message;
				String msg = textMessage.getText();
				uuid = XMLMessageParser.findAttribute(msg, "sc|uuid");
//				uuid = textMessage.getText();
				System.out.println("Get A Responce: " + uuid);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception for OnMessage!");
			}
		}
	}


}
