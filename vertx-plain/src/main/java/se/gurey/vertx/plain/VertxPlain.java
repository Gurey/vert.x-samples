package se.gurey.vertx.plain;

import java.util.LinkedHashMap;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;

public class VertxPlain extends AbstractVerticle {
	
	private final LinkedHashMap<Integer, Name> names = new LinkedHashMap<>();
	
	@Override
	public void start(Future<Void> fut) throws Exception {
		
		names.put(1, new Name("Gustav"));
		names.put(2, new Name("Magnus"));
		names.put(3, new Name("Birtu"));
		
		Router router = Router.router(vertx);
		
		router.route("/").handler(routeContext -> {
			HttpServerResponse response = routeContext.response();
			response
				.putHeader("Content-Type", "text/html")
				.end("<h1>Hello!</h1>");
		});
		
		router.get("/api/names").handler(rc -> {
			HttpServerResponse response = rc.response();
			response.putHeader("Content-Type", "application/json")
			.end(Json.encodePrettily(names.values()));
		});
		
		router.get("/api/names/:id").handler(rc -> rc.response().end(
				Json.encodePrettily(
						names.get(Integer.valueOf(rc.request().getParam("id")))))
				);
		
		vertx.
			createHttpServer()
			.requestHandler(router::accept)
			.listen(config().getInteger("port"), result -> {
				if(result.succeeded()){
					fut.complete();
				} else {
					fut.fail(result.cause());
				}
			});
		
	}

}
