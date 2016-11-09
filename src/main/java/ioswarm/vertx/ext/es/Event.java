package ioswarm.vertx.ext.es;

import java.io.Serializable;
import java.time.Instant;

import io.vertx.core.eventbus.Message;

public class Event<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3706049698832767106L;

	private String id;
	private Instant eventDate;
	private String command;
	private T content;
	private Message<T> message;
	
	public Event() {}
	
	public Event(String id, Instant eventDate, String command, T content) {
		this();
		setId(id);
		setEventDate(eventDate);
		setCommand(command);
		setContent(content);
	}
	
	public Event(String id, Instant eventDate, String command, Message<T> msg) {
		this();
		setId(id);
		setEventDate(eventDate);
		setCommand(command);
		setMessage(msg);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Instant getEventDate() {
		return eventDate;
	}
	public void setEventDate(Instant eventDate) {
		this.eventDate = eventDate;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}

	public Message<T> getMessage() {
		return message;
	}
	public void setMessage(Message<T> message) {
		if (message.body() != null) setContent(message.body());
		this.message = message;
	}
	
	public void reply() {
		reply(getContent());			
	}
	
	public void reply(Object o) {
		if (getMessage() != null) 
			getMessage().reply(o);
	}
	
}
