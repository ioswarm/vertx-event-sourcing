package ioswarm.vertx.ext.es;

import io.vertx.core.Verticle;

public interface ESVerticle<T> extends Verticle {
	
	public String scope();
	
	public String id();
	
	public T persist(T t) throws Exception;
	
	public void recover(T t);
	
	public void receive(T t) throws Exception;
	
	public void snapshot(T t) throws Exception;
	
}
