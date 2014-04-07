package catan.server;

import java.io.IOException;
import java.net.InetSocketAddress;

// HANDLERS
import catan.server.handler.FileDownloadHandlerFactory;
import catan.server.handler.GameHandler;
import catan.server.handler.GamesHandler;
import catan.server.handler.HandlerModule_Prod;
import catan.server.handler.MovesHandler;
import catan.server.handler.UserHandler;
import catan.server.handler.UtilChangeLogLevelHandler;
//Persistence Provider Factory
import catan.server.factory.*;

// GOOGLE 
import com.google.inject.Guice;
import com.google.inject.Injector;
// HTTP
import com.sun.net.httpserver.HttpServer;


public class Server {

	// Static constants
	private static final int DEFAULT_PORT = 8081;
	private static final int DEFAULT_QUEUE_SIZE = 10;

	// Static mutables
	private static boolean debugEnabled = false;

	public static boolean isDebugEnabled() {
		return Server.debugEnabled;
	}

	public static void setDebugEnabled(boolean debugEnabled) {
		Server.debugEnabled = debugEnabled;
	}

	public static void println(String str) {
		if (debugEnabled) System.out.println(str);
	}

	// Instance constants
	private final int port;
	private final int queueSize;
	private final HttpServer server;
	private final Injector injector;
	private final FileDownloadHandlerFactory fdhFactory;

	public Server(Integer port, Integer queueSize) {
		injector = Guice.createInjector(new HandlerModule_Prod());
		fdhFactory = injector.getInstance(FileDownloadHandlerFactory.class);
		// Initialize fields
		this.port = (port == null) ? DEFAULT_PORT : port;
		this.queueSize = (queueSize == null) ? DEFAULT_QUEUE_SIZE : queueSize;
		// Create the server
		if (debugEnabled) System.out.println("Creating server: Port " + this.port + ", queue size " + this.queueSize + "...");
		HttpServer tempServer = null;
		try {
			tempServer = HttpServer.create(new InetSocketAddress(this.port), this.queueSize);
		} catch (IOException e) {
			if (debugEnabled) System.out.println("Failed to create server.");
			e.printStackTrace();
		}
		server = tempServer;
		if (debugEnabled) System.out.println("Done.");
		// Use the default executor
		server.setExecutor(null);
		// Initialize handlers
		initHandlers();
	}

	public int getPort() {
		return port;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void start() {
		if (debugEnabled) System.out.println("Starting server...");
		server.start();
		if (debugEnabled) System.out.println("Done.");
	}

	public void stop(int delay) {
		server.stop(delay);
	}

	private void initHandlers() {
		if (debugEnabled) System.out.println("Initializing handlers...");
		// Initialize file download handlers
		server.createContext("/", fdhFactory.create("/", "gameplay"));
		server.createContext("/docs", fdhFactory.create("/docs", "docs"));
		// Initialize API handlers
		server.createContext("/user/", new UserHandler());
		server.createContext("/games/", new GamesHandler());
		server.createContext("/game/", new GameHandler());
		server.createContext("/util/changeLogLevel", injector.getInstance(UtilChangeLogLevelHandler.class));
		server.createContext("/moves/", new MovesHandler());
		if (debugEnabled) System.out.println("Done.");
	}

	// Entry point
	public static void main(String[] args) {
		Integer port = (args.length > 0) ? Integer.parseInt(args[0]) : null;
		Server.setDebugEnabled(true);
		Server server = new Server(port, null);
		server.start();
	}
}
