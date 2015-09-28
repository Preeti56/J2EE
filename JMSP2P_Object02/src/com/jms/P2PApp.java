package com.jms;
import java.io.Serializable;


public class P2PApp implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public P2PApp(String name, String message) {
		this.name = name;
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private String name;
	private String message;

}
