package se.gurey.vertx.jersey.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloWorldResource {

	@GET
	public String hello(){
		return "HelloWorld";
	}
	
}
