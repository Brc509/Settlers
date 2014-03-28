package catan.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

public class MockExchange extends HttpExchange{
	
	private String requestBody = "";
	private URI uri;
	
	public void setUri (URI uri) {
		this.uri = uri;
	}
	
	public void setRequestBody (String str){
		requestBody = str;
	}
	
	@Override
	public URI getRequestURI() {
		return uri;
	}

	@Override
	public InputStream getRequestBody() {
		InputStream is = new ByteArrayInputStream(requestBody.getBytes());
		return is;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpContext getHttpContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InetSocketAddress getLocalAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpPrincipal getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InetSocketAddress getRemoteAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Headers getRequestHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream getResponseBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResponseCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Headers getResponseHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendResponseHeaders(int arg0, long arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStreams(InputStream arg0, OutputStream arg1) {
		// TODO Auto-generated method stub
		
	}

}
