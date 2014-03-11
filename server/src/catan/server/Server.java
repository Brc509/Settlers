package catan.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import catan.server.handler.FileDownloadHandler;
import catan.server.handler.GameAddAIHandler;
import catan.server.handler.GameCommandsHandler;
import catan.server.handler.GameListAIHandler;
import catan.server.handler.GameModelHandler;
import catan.server.handler.GameResetHandler;
import catan.server.handler.GamesCreateHandler;
import catan.server.handler.GamesJoinHandler;
import catan.server.handler.GamesListHandler;
import catan.server.handler.MovesAcceptTradeHandler;
import catan.server.handler.MovesBuildCityHandler;
import catan.server.handler.MovesBuildRoadHandler;
import catan.server.handler.MovesBuildSettlementHandler;
import catan.server.handler.MovesBuyDevCardHandler;
import catan.server.handler.MovesDiscardCardsHandler;
import catan.server.handler.MovesFinishTurnHandler;
import catan.server.handler.MovesMaritimeTradeHandler;
import catan.server.handler.MovesMonopolyHandler;
import catan.server.handler.MovesMonumentHandler;
import catan.server.handler.MovesOfferTradeHandler;
import catan.server.handler.MovesRoadBuildingHandler;
import catan.server.handler.MovesRobPlayerHandler;
import catan.server.handler.MovesRollNumberHandler;
import catan.server.handler.MovesSendChatHandler;
import catan.server.handler.MovesSoldierHandler;
import catan.server.handler.MovesYearOfPlentyHandler;
import catan.server.handler.UserLoginHandler;
import catan.server.handler.UserRegisterHandler;
import catan.server.handler.UtilChangeLogLevelHandler;

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

	// Instance constants
	private final int port;
	private final int queueSize;
	private final HttpServer server;

	public Server(Integer port, Integer queueSize) {
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
		server.createContext("/", new FileDownloadHandler(this, "/", "gameplay"));
		server.createContext("/docs", new FileDownloadHandler(this, "/docs", "docs"));
		// Initialize API handlers
		server.createContext("/user/login", new UserLoginHandler(this));
		server.createContext("/user/register", new UserRegisterHandler(this));
		server.createContext("/games/list", new GamesListHandler(this));
		server.createContext("/games/create", new GamesCreateHandler(this));
		server.createContext("/games/join", new GamesJoinHandler(this));
		server.createContext("/game/model", new GameModelHandler(this));
		server.createContext("/game/reset", new GameResetHandler(this));
		server.createContext("/game/commands", new GameCommandsHandler(this));
		server.createContext("/game/addAI", new GameAddAIHandler(this));
		server.createContext("/game/listAI", new GameListAIHandler(this));
		server.createContext("/util/changeLogLevel", new UtilChangeLogLevelHandler(this));
		server.createContext("/moves/sendChat", new MovesSendChatHandler(this));
		server.createContext("/moves/rollNumber", new MovesRollNumberHandler(this));
		server.createContext("/moves/robPlayer", new MovesRobPlayerHandler(this));
		server.createContext("/moves/finishTurn", new MovesFinishTurnHandler(this));
		server.createContext("/moves/buyDevCard", new MovesBuyDevCardHandler(this));
		server.createContext("/moves/Year_of_Plenty", new MovesYearOfPlentyHandler(this));
		server.createContext("/moves/Road_Building", new MovesRoadBuildingHandler(this));
		server.createContext("/moves/Soldier", new MovesSoldierHandler(this));
		server.createContext("/moves/Monopoly", new MovesMonopolyHandler(this));
		server.createContext("/moves/Monument", new MovesMonumentHandler(this));
		server.createContext("/moves/buildRoad", new MovesBuildRoadHandler(this));
		server.createContext("/moves/buildSettlement", new MovesBuildSettlementHandler(this));
		server.createContext("/moves/buildCity", new MovesBuildCityHandler(this));
		server.createContext("/moves/offerTrade", new MovesOfferTradeHandler(this));
		server.createContext("/moves/acceptTrade", new MovesAcceptTradeHandler(this));
		server.createContext("/moves/maritimeTrade", new MovesMaritimeTradeHandler(this));
		server.createContext("/moves/discardCards", new MovesDiscardCardsHandler(this));
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
