import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class P2P_MDBClient {
	public static final String QUEUE = "queue/PJMDBQueue";

	public static void main(String[] args) throws NamingException, JMSException {
		System.out.println("----Entering JMS P2P MDB Client---");

		// Obtain a JNDI connection
		Context initialContext = P2P_MDBClient.getInitialContext();
		System.out.println("Initial Context created..");

		// Lookup a JMS Connection factory
		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) initialContext
				.lookup("jms/GFQueueConnectionFactory");
		System.out.println("Lookup of Connection Factory complete..");

		// Looking up a JMS Queue
		Queue queue = (Queue) initialContext.lookup(P2P_MDBClient.QUEUE);
		System.out.println("Lookup of Queue done...");

		// Create a JMS connection
		QueueConnection queueConnection = queueConnectionFactory
				.createQueueConnection();
		System.out.println("Connection available...");

		// Creating Session object
		QueueSession queueSession = queueConnection.createQueueSession(false,
				Session.AUTO_ACKNOWLEDGE);
		System.out.println("Session is ready...");

		// Starting the Connection
		queueConnection.start();

		// Creating Subscriber and sending a message
		P2P_MDBClient sender = new P2P_MDBClient();
		System.out.println("Sender is ready to send...");
		sender.sendMessage("---Hello From MDB Queue Client", queueSession,
				queue);

		System.out.println("----Exiting JMS P2P MDB Client----");
	}

	// Create and send message using Queue Sender
	protected void sendMessage(String text, QueueSession queueSess, Queue queue)
			throws JMSException {
		QueueSender sender = queueSess.createSender(queue);
		TextMessage textMessage = queueSess.createTextMessage(text);
		sender.send(textMessage);
		System.out.println("Message sent...");
		sender.close();
	}

	// Specifying the JNDI properties specific to vendor
	public static Context getInitialContext() throws NamingException {
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial",
				"com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("java.naming.factory.url.pkgs",
				"com.sun.enterprise.naming");
		props.setProperty("java.naming.provider.url", "http://localhost:8080");
		Context context = new InitialContext(props);
		return context;
	}

}