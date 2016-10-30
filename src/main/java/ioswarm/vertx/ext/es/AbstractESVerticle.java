package ioswarm.vertx.ext.es;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

public abstract class AbstractESVerticle<T> extends AbstractVerticle implements ESVerticle<T> {

	@Override
	public void start() throws Exception {
		EventBus eb = vertx.eventBus();
		eb.consumer(id(), this::handleReceiveEvent);
	}
	
	public void receive(final T t) throws Exception {
		vertx.executeBlocking((Future<T> f) -> {
			try {
				f.complete(persist(t));
			} catch(Exception e) {
				f.fail(e);
			}
		}, (AsyncResult<T> res) -> {
			if (res.succeeded())
				recover(res.result());
		});
	}
	
	public void handleReceiveEvent(Message<T> msg) {
		try {
			receive(msg.body());
			msg.reply(true);
		} catch(Exception e) {
			msg.fail(-1, e.getMessage());
		}
	}
	
	
}
