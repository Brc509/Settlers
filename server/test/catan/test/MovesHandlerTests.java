package catan.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import org.junit.Before;
import org.junit.Test;

import catan.server.handler.MovesHandler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

public class MovesHandlerTests {
	private HttpExchange exchange;
	private MovesHandler handler;
	@Before 
	public void beforeTest() {
		exchange = new MockExchange();
		handler = new MovesHandler();
	}
	
	@Test
	public void test() throws IOException {
		handler.handle(exchange);
	}
	
	@Test
	public void rollNumber() {
		
	}

}
