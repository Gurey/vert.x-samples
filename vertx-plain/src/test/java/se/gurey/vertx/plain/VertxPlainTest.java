package se.gurey.vertx.plain;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
public class VertxPlainTest {
	
	private Vertx vertx;
	private int socket;

	@Before
	public void setUp(TestContext context) throws IOException {
		vertx = Vertx.vertx();
		ServerSocket server = new ServerSocket(0);
		socket = server.getLocalPort();
		server.close();
		
		DeploymentOptions options = new DeploymentOptions().setConfig(new JsonObject()
				.put("port", socket));
		vertx.deployVerticle(VertxPlain.class.getName(), options, context.asyncAssertSuccess());
	}

	@After
	public void tearDown(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}
	
	@Test
	public void testAll(TestContext context){
		final Async async = context.async();
	    vertx.createHttpClient().getNow(socket, "localhost", "/api/names",
	     response -> {
	      response.handler(body -> {
	        context.assertTrue(body.toString().contains("Gustav"));
	        async.complete();
	      });
	    });
	}
	
	@Test
	public void testGustav(TestContext context){
		final Async async = context.async();
	    vertx.createHttpClient().getNow(socket, "localhost", "/api/names/1",
	     response -> {
	      response.handler(body -> {
	        context.assertTrue(body.toString().contains("Gustav"));
	        async.complete();
	      });
	    });
	}
	
	@Test
	public void testMagnus(TestContext context){
		final Async async = context.async();
	    vertx.createHttpClient().getNow(socket, "localhost", "/api/names/2",
	     response -> {
	      response.handler(body -> {
	        context.assertTrue(body.toString().contains("Magnus"));
	        async.complete();
	      });
	    });
	}
	
	@Test
	public void testBirtu(TestContext context){
		final Async async = context.async();
	    vertx.createHttpClient().getNow(socket, "localhost", "/api/names/3",
	     response -> {
	      response.handler(body -> {
	        context.assertTrue(body.toString().contains("Birtu"));
	        async.complete();
	      });
	    });
	}

}
