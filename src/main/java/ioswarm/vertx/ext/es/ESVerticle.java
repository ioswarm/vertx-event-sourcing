package ioswarm.vertx.ext.es;

import io.vertx.core.Verticle;

public interface ESVerticle<T> extends Verticle {
	
	public String scope();
	
	public String id();
	
	default public String scopeAddress() { return "__ioswarm.es."+scope(); }
	default public String address() { return scopeAddress()+"."+id(); }
	
	public Event<T> persist(Event<T> evt) throws Exception;
	
	public void recover(Event<T> evt);
	
	public void receive(Event<T> t) throws Exception;
	
	public void snapshot() throws Exception;
	
}
