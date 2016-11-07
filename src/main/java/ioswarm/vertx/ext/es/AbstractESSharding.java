package ioswarm.vertx.ext.es;

import ioswarm.vertx.service.ShardingService;

public abstract class AbstractESSharding<T> extends ShardingService<T> {

	public abstract String scope();
	
	public String scopeAddress() { return "__ioswarm.es."+scope(); }
	
	@Override
	public String address() { return scopeAddress(); }
	
}
