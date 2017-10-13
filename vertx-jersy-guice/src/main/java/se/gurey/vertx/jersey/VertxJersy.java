package se.gurey.vertx.jersey;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import com.englishtown.vertx.hk2.HK2JerseyBinder;
import com.englishtown.vertx.hk2.HK2VertxBinder;
import com.englishtown.vertx.jersey.JerseyServer;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import se.gurey.vertx.jersey.resources.HelloWorldResource;

public class VertxJersy {

	public static void main(String[] args) {
		final Vertx vertx = Vertx.factory.vertx();
		
		vertx.runOnContext(aVoid -> {
			vertx.getOrCreateContext()
				.config().put("jersey", new JsonObject()
						.put("port", 8080)
						.put("packages", new JsonArray()
								.add(HelloWorldResource.class.getPackage().getName())));
			
		ServiceLocator locator = ServiceLocatorUtilities.bind(,new HK2JerseyBinder(), new HK2VertxBinder(vertx));
		JerseyServer server = locator.getService(JerseyServer.class);
		
		server.start();
		});
	}

}
