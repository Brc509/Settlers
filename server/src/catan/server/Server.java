package catan.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import catan.server.handler.FileDownloadHandlerFactory;
import catan.server.handler.HandlerModule_Prod;
import catan.server.handler.UserHandler;
import catan.server.handler.UtilChangeLogLevelHandler;
import catan.server.handler.game.GameAddAIHandler;
import catan.server.handler.game.GameCommandsHandler;
import catan.server.handler.game.GameListAIHandler;
import catan.server.handler.game.GameModelHandler;
import catan.server.handler.game.GameResetHandler;
import catan.server.handler.games.GamesCreateHandler;
import catan.server.handler.games.GamesJoinHandler;
import catan.server.handler.games.GamesListHandler;
import catan.server.handler.moves.MovesAcceptTradeHandler;
import catan.server.handler.moves.MovesBuildCityHandler;
import catan.server.handler.moves.MovesBuildRoadHandler;
import catan.server.handler.moves.MovesBuildSettlementHandler;
import catan.server.handler.moves.MovesBuyDevCardHandler;
import catan.server.handler.moves.MovesDiscardCardsHandler;
import catan.server.handler.moves.MovesFinishTurnHandler;
import catan.server.handler.moves.MovesMaritimeTradeHandler;
import catan.server.handler.moves.MovesMonopolyHandler;
import catan.server.handler.moves.MovesMonumentHandler;
import catan.server.handler.moves.MovesOfferTradeHandler;
import catan.server.handler.moves.MovesRoadBuildingHandler;
import catan.server.handler.moves.MovesRobPlayerHandler;
import catan.server.handler.moves.MovesRollNumberHandler;
import catan.server.handler.moves.MovesSendChatHandler;
import catan.server.handler.moves.MovesSoldierHandler;
import catan.server.handler.moves.MovesYearOfPlentyHandler;

import com.google.inject.Guice;
import com.google.inject.Injector;
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
		server.createContext("/user", new UserHandler());
//		server.createContext("/user/login", injector.getInstance(UserLoginHandler.class));
//		server.createContext("/user/register", injector.getInstance(UserRegisterHandler.class));
		server.createContext("/games/list", injector.getInstance(GamesListHandler.class));
		server.createContext("/games/create", injector.getInstance(GamesCreateHandler.class));
		server.createContext("/games/join", injector.getInstance(GamesJoinHandler.class));
		server.createContext("/game/model", injector.getInstance(GameModelHandler.class));
		server.createContext("/game/reset", injector.getInstance(GameResetHandler.class));
		server.createContext("/game/commands", injector.getInstance(GameCommandsHandler.class));
		server.createContext("/game/addAI", injector.getInstance(GameAddAIHandler.class));
		server.createContext("/game/listAI", injector.getInstance(GameListAIHandler.class));
		server.createContext("/util/changeLogLevel", injector.getInstance(UtilChangeLogLevelHandler.class));
		server.createContext("/moves/sendChat", injector.getInstance(MovesSendChatHandler.class));
		server.createContext("/moves/rollNumber", injector.getInstance(MovesRollNumberHandler.class));
		server.createContext("/moves/robPlayer", injector.getInstance(MovesRobPlayerHandler.class));
		server.createContext("/moves/finishTurn", injector.getInstance(MovesFinishTurnHandler.class));
		server.createContext("/moves/buyDevCard", injector.getInstance(MovesBuyDevCardHandler.class));
		server.createContext("/moves/Year_of_Plenty", injector.getInstance(MovesYearOfPlentyHandler.class));
		server.createContext("/moves/Road_Building", injector.getInstance(MovesRoadBuildingHandler.class));
		server.createContext("/moves/Soldier", injector.getInstance(MovesSoldierHandler.class));
		server.createContext("/moves/Monopoly", injector.getInstance(MovesMonopolyHandler.class));
		server.createContext("/moves/Monument", injector.getInstance(MovesMonumentHandler.class));
		server.createContext("/moves/buildRoad", injector.getInstance(MovesBuildRoadHandler.class));
		server.createContext("/moves/buildSettlement", injector.getInstance(MovesBuildSettlementHandler.class));
		server.createContext("/moves/buildCity", injector.getInstance(MovesBuildCityHandler.class));
		server.createContext("/moves/offerTrade", injector.getInstance(MovesOfferTradeHandler.class));
		server.createContext("/moves/acceptTrade", injector.getInstance(MovesAcceptTradeHandler.class));
		server.createContext("/moves/maritimeTrade", injector.getInstance(MovesMaritimeTradeHandler.class));
		server.createContext("/moves/discardCards", injector.getInstance(MovesDiscardCardsHandler.class));
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
