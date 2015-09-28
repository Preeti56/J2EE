package com.jms.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: MyMDB01
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "PJMDBTopic"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") }, mappedName = "PJMDBTopic")
public class MyMDB01 implements MessageListener {
	
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			try {
				System.out.println("From MDB01: " + textMessage.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
