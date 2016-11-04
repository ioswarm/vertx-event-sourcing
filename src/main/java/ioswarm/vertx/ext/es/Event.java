package ioswarm.vertx.ext.es;

import java.io.Serializable;
import java.time.Instant;

public class Event<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3706049698832767106L;

	private String id;
	private Instant eventData;
	private String command;
	private T content;
	
	public Event() {}
	
	public Event(String id, Instant eventDate, String command, T content) {
		this();
		setId(id);
		setEventData(eventDate);
		setCommand(command);
		setContent(content);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Instant getEventData() {
		return eventData;
	}
	public void setEventData(Instant eventData) {
		this.eventData = eventData;
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
	
}
