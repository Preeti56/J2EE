import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.NamingException;

public class TopicConsumerOne implements MessageListener {
	public static final String TOPIC = "topic/PJPubSubTopic";

	public static void main(String[] args) throws JMSException,
	NamingException, InterruptedException {
		System.out.println("----Entering JMS TopicConsumerOne----");

		// Obtain a JNDI connection
		Context initialContext = TopicProducer.getInitialContext();
		System.out.println("Initial Context created..");

		// Lookup a JMS Connection factory
		TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) initialContext
				.lookup("jms/GFConnectionFactory");
		System.out.println("Lookup of Connection Factory complete..");

		// Looking up a JMS topic
		Topic topic = (Topic) initialContext.lookup(TopicConsumerOne.TOPIC);
		System.out.println("Lookup of Topic done...");

		// Create a JMS connection
		TopicConnection topicConnection = topicConnectionFactory
				.createTopicConnection();
		System.out.println("Connection available...");

		// Creating Session object
		TopicSession topicSession = topicConnection.createTopicSession(false,
				Session.AUTO_ACKNOWLEDGE);
		System.out.println("Session is ready...");

		// Creating Subscriber and setting a JMS message listener
		topicSession.createSubscriber(topic).setMessageListener(
				new TopicConsumerOne());
		System.out.println("Subscriber is ready to receive...");

		// Starting the Connection
		topicConnection.start();

		while (true) {
			Thread.sleep(1);
		}

	}

	@Override
	public void onMessage(Message msg) {
		try {
			System.out.println("Incoming msgs for TopicConsumer1: "
					+ ((TextMessage) msg).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
