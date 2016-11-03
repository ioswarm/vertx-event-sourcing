package ioswarm.vertx.ext.es;

import io.vertx.core.Vertx;

public class EventHandler<T> {

	private final Vertx vertx;
	private final String scope;
	
	public EventHandler(Vertx vertx, String scope) {
		this.vertx = vertx;
		this.scope = scope;
	}
	
	public Vertx getVertx() { return vertx; } 
	
	public String getScope() { return scope; }
	
	
	
}
