package ioswarm.vertx.ext.es;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;

public class EventHandler<T> {

	private final Vertx vertx;
	private final String scope;
	
	public EventHandler(Vertx vertx, String scope) {
		this.vertx = vertx;
		this.scope = scope;
	}
	
	public Vertx getVertx() { return vertx; } 
	
	public String getScope() { return scope; }
	
	public String scopeAddress() { return "__ioswarm.es."+getScope(); }
	public String address(String id) { return scopeAddress()+"."+id; }
	
	public void send(String command, String id, T t) {
		send(command, id, t, null);
	}
	
	public void send(String command, String id, T t, Handler<AsyncResult<Message<Void>>> replyHandler) {
		DeliveryOptions opt = new DeliveryOptions()
				.addHeader("command", command)
				.addHeader("shard", id);
		vertx.eventBus().send(address(id), t, opt, replyHandler);
	}
	
}
