package ioswarm.vertx.ext.es;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Instant;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import ioswarm.vertx.service.Service;

public abstract class AbstractESVerticle<T> extends Service implements ESVerticle<T> {
	
	@Override
	public void start() throws Exception {
		EventBus eb = vertx.eventBus();
		eb.consumer(address(), this::handleReceiveEvent);
	}
	
	@Override
	public void receive(final Event<T> t) throws Exception {
		vertx.executeBlocking((Future<Event<T>> f) -> {
			try {
				f.complete(persist(t));
			} catch(Exception e) {
				f.fail(e);
			}
		}, (AsyncResult<Event<T>> res) -> {
			if (res.succeeded())
				recover(res.result());
			else
				res.cause().printStackTrace();
		});
	}
	
	public void handleReceiveEvent(Message<T> msg) {
		try {
			System.out.println("receive command ... ");
			String cmd = msg.headers().get("command");
			receive(new Event<T>(id(), Instant.now(), cmd == null ? "unknown" : cmd, msg.body()));
			msg.reply(true);
		} catch(Exception e) {
			msg.fail(-1, e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public T unmarshall(/*Class<T> clazz, */byte[] data) throws IOException {
		Object ret = null;
		if (data != null) {
			try {
				ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
				ret = oin.readObject();
				oin.close();
			} catch(Exception e) {
				throw new IOException("ERROR while unmarshalling ... ", e);
			}
		}
		return ret == null ? null : (T)ret /* clazz.cast(ret) */;
	}
	
	public byte[] marshall(T t) throws IOException {
		if (t == null) return null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(t);
			oos.flush();
			oos.close();
			bos.close();
			return bos.toByteArray();
		} catch(Exception e) {
			throw new IOException("ERROR while marshalling ... ", e);
		}
	}
	
}
