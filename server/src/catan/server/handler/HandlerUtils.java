package catan.server.handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import catan.server.Cookie;
import catan.server.RegisteredUser;
import catan.server.RegisteredUsers;
import catan.server.command.Command;
import catan.server.command.moves.AcceptTradeCommand;
import catan.server.command.moves.BuildCityCommand;
import catan.server.command.moves.BuildRoadCommand;
import catan.server.command.moves.BuildSettlementCommand;
import catan.server.command.moves.BuyDevCardCommand;
import catan.server.command.moves.DiscardCardsCommand;
import catan.server.command.moves.FinishTurnCommand;
import catan.server.command.moves.MaritimeTradeCommand;
import catan.server.command.moves.MonopolyCommand;
import catan.server.command.moves.MonumentCommand;
import catan.server.command.moves.OfferTradeCommand;
import catan.server.command.moves.RoadBuildingCommand;
import catan.server.command.moves.RobPlayerCommand;
import catan.server.command.moves.RollNumberCommand;
import catan.server.command.moves.SendChatCommand;
import catan.server.command.moves.SoldierCommand;
import catan.server.command.moves.YearOfPlentyCommand;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;

/**
 * Provides methods that facilitate <code>HttpHandler</code> operations.
 * 
 * @author Spencer Bench
 */
public class HandlerUtils {

	// Static constants
	private static final byte[] BUFFER = new byte[1048576]; // 1 MiB buffer
	private static final String SAMPLE_MODEL = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":10,\"roadBuilding\":1,\"monument\":4},\"map\":{\"hexGrid\":{\"hexes\":[[{\"isLand\":false,\"location\":{\"x\":0,\"y\":-3},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"isLand\":false,\"location\":{\"x\":1,\"y\":-3},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"isLand\":false,\"location\":{\"x\":2,\"y\":-3},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"isLand\":false,\"location\":{\"x\":3,\"y\":-3},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]}],[{\"isLand\":false,\"location\":{\"x\":-1,\"y\":-2},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"isLand\":true,\"location\":{\"x\":0,\"y\":-2},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Brick\",\"isLand\":true,\"location\":{\"x\":1,\"y\":-2},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":3}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Wood\",\"isLand\":true,\"location\":{\"x\":2,\"y\":-2},\"vertexes\":[{\"value\":{\"worth\":1,\"ownerID\":3}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":3}}]},{\"isLand\":false,\"location\":{\"x\":3,\"y\":-2},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":2,\"ownerID\":0}}],\"edges\":[{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":0}}]}],[{\"isLand\":false,\"location\":{\"x\":-2,\"y\":-1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Brick\",\"isLand\":true,\"location\":{\"x\":-1,\"y\":-1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Wood\",\"isLand\":true,\"location\":{\"x\":0,\"y\":-1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Ore\",\"isLand\":true,\"location\":{\"x\":1,\"y\":-1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":3}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":2}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":3}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":2}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Sheep\",\"isLand\":true,\"location\":{\"x\":2,\"y\":-1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":2,\"ownerID\":0}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":-1}}]},{\"isLand\":false,\"location\":{\"x\":3,\"y\":-1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":2,\"ownerID\":0}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]}],[{\"isLand\":false,\"location\":{\"x\":-3,\"y\":0},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Ore\",\"isLand\":true,\"location\":{\"x\":-2,\"y\":0},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Sheep\",\"isLand\":true,\"location\":{\"x\":-1,\"y\":0},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Wheat\",\"isLand\":true,\"location\":{\"x\":0,\"y\":0},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":2}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":2}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":2}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Brick\",\"isLand\":true,\"location\":{\"x\":1,\"y\":0},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":2}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":2}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Wheat\",\"isLand\":true,\"location\":{\"x\":2,\"y\":0},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":2,\"ownerID\":0}}],\"edges\":[{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":0}}]},{\"isLand\":false,\"location\":{\"x\":3,\"y\":0},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]}],[{\"isLand\":false,\"location\":{\"x\":-3,\"y\":1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Wheat\",\"isLand\":true,\"location\":{\"x\":-2,\"y\":1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":1}}]},{\"landtype\":\"Sheep\",\"isLand\":true,\"location\":{\"x\":-1,\"y\":1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":2}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":3}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":3}}]},{\"landtype\":\"Wood\",\"isLand\":true,\"location\":{\"x\":0,\"y\":1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":2}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":0}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":2}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Sheep\",\"isLand\":true,\"location\":{\"x\":1,\"y\":1},\"vertexes\":[{\"value\":{\"worth\":1,\"ownerID\":0}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":2,\"ownerID\":0}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"isLand\":false,\"location\":{\"x\":2,\"y\":1},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":2,\"ownerID\":0}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]}],[{\"isLand\":false,\"location\":{\"x\":-3,\"y\":2},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Wood\",\"isLand\":true,\"location\":{\"x\":-2,\"y\":2},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":3}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":3}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Ore\",\"isLand\":true,\"location\":{\"x\":-1,\"y\":2},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":3}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"landtype\":\"Wheat\",\"isLand\":true,\"location\":{\"x\":0,\"y\":2},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":1,\"ownerID\":0}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":0}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"isLand\":false,\"location\":{\"x\":1,\"y\":2},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]}],[{\"isLand\":false,\"location\":{\"x\":-3,\"y\":3},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"isLand\":false,\"location\":{\"x\":-2,\"y\":3},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"isLand\":false,\"location\":{\"x\":-1,\"y\":3},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]},{\"isLand\":false,\"location\":{\"x\":0,\"y\":3},\"vertexes\":[{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}},{\"value\":{\"worth\":0,\"ownerID\":-1}}],\"edges\":[{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}},{\"value\":{\"ownerID\":-1}}]}]],\"offsets\":[3,2,1,0,0,0,0],\"radius\":4,\"x0\":3,\"y0\":3},\"radius\":4,\"numbers\":{\"2\":[{\"x\":-2,\"y\":1}],\"3\":[{\"x\":-1,\"y\":2},{\"x\":0,\"y\":-1}],\"4\":[{\"x\":1,\"y\":-2},{\"x\":0,\"y\":1}],\"5\":[{\"x\":1,\"y\":0},{\"x\":-2,\"y\":0}],\"6\":[{\"x\":2,\"y\":0},{\"x\":-2,\"y\":2}],\"8\":[{\"x\":0,\"y\":2},{\"x\":-1,\"y\":-1}],\"9\":[{\"x\":1,\"y\":-1},{\"x\":-1,\"y\":1}],\"10\":[{\"x\":1,\"y\":1},{\"x\":-1,\"y\":0}],\"11\":[{\"x\":2,\"y\":-2},{\"x\":0,\"y\":0}],\"12\":[{\"x\":2,\"y\":-1}]},\"ports\":[{\"ratio\":3,\"validVertex1\":{\"direction\":\"SW\",\"x\":3,\"y\":-3},\"validVertex2\":{\"direction\":\"W\",\"x\":3,\"y\":-3},\"orientation\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":2,\"inputResource\":\"Wheat\",\"validVertex1\":{\"direction\":\"SE\",\"x\":-1,\"y\":-2},\"validVertex2\":{\"direction\":\"SW\",\"x\":-1,\"y\":-2},\"orientation\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"inputResource\":\"Wood\",\"validVertex1\":{\"direction\":\"NE\",\"x\":-3,\"y\":2},\"validVertex2\":{\"direction\":\"E\",\"x\":-3,\"y\":2},\"orientation\":\"NE\",\"location\":{\"x\":-3,\"y\":2}},{\"ratio\":3,\"validVertex1\":{\"direction\":\"NW\",\"x\":0,\"y\":3},\"validVertex2\":{\"direction\":\"NE\",\"x\":0,\"y\":3},\"orientation\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"inputResource\":\"Brick\",\"validVertex1\":{\"direction\":\"NE\",\"x\":-2,\"y\":3},\"validVertex2\":{\"direction\":\"E\",\"x\":-2,\"y\":3},\"orientation\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":3,\"validVertex1\":{\"direction\":\"E\",\"x\":-3,\"y\":0},\"validVertex2\":{\"direction\":\"SE\",\"x\":-3,\"y\":0},\"orientation\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"inputResource\":\"Ore\",\"validVertex1\":{\"direction\":\"SE\",\"x\":1,\"y\":-3},\"validVertex2\":{\"direction\":\"SW\",\"x\":1,\"y\":-3},\"orientation\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":2,\"inputResource\":\"Sheep\",\"validVertex1\":{\"direction\":\"W\",\"x\":3,\"y\":-1},\"validVertex2\":{\"direction\":\"NW\",\"x\":3,\"y\":-1},\"orientation\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":3,\"validVertex1\":{\"direction\":\"W\",\"x\":2,\"y\":1},\"validVertex2\":{\"direction\":\"NW\",\"x\":2,\"y\":1},\"orientation\":\"NW\",\"location\":{\"x\":2,\"y\":1}}],\"robber\":{\"x\":1,\"y\":-1}},\"players\":[{\"MAX_GAME_POINTS\":10,\"resources\":{\"brick\":14,\"wood\":13,\"sheep\":15,\"wheat\":10,\"ore\":8},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":2,\"roadBuilding\":0,\"monument\":1},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":1,\"roadBuilding\":1,\"monument\":0},\"roads\":8,\"cities\":2,\"settlements\":4,\"soldiers\":1,\"victoryPoints\":7,\"monuments\":0,\"longestRoad\":true,\"largestArmy\":false,\"playedDevCard\":true,\"discarded\":false,\"playerID\":0,\"orderNumber\":0,\"name\":\"Sam\",\"color\":\"orange\"},{\"MAX_GAME_POINTS\":10,\"resources\":{\"brick\":1,\"wood\":0,\"sheep\":1,\"wheat\":0,\"ore\":6},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"longestRoad\":false,\"largestArmy\":false,\"playedDevCard\":false,\"discarded\":false,\"playerID\":1,\"orderNumber\":1,\"name\":\"Brooke\",\"color\":\"blue\"},{\"MAX_GAME_POINTS\":10,\"resources\":{\"brick\":5,\"wood\":1,\"sheep\":0,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"longestRoad\":false,\"largestArmy\":false,\"playedDevCard\":false,\"discarded\":false,\"playerID\":10,\"orderNumber\":2,\"name\":\"Pete\",\"color\":\"red\"},{\"MAX_GAME_POINTS\":10,\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":0,\"ore\":2},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"longestRoad\":false,\"largestArmy\":false,\"playedDevCard\":false,\"discarded\":false,\"playerID\":11,\"orderNumber\":3,\"name\":\"Mark\",\"color\":\"green\"}],\"log\":{\"lines\":[{\"source\":\"Sam\",\"message\":\"Sambuiltaroad\"},{\"source\":\"Sam\",\"message\":\"Sambuiltasettlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027sturnjustended\"},{\"source\":\"Brooke\",\"message\":\"Brookebuiltaroad\"},{\"source\":\"Brooke\",\"message\":\"Brookebuiltasettlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027sturnjustended\"},{\"source\":\"Pete\",\"message\":\"Petebuiltaroad\"},{\"source\":\"Pete\",\"message\":\"Petebuiltasettlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027sturnjustended\"},{\"source\":\"Mark\",\"message\":\"Markbuiltaroad\"},{\"source\":\"Mark\",\"message\":\"Markbuiltasettlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027sturnjustended\"},{\"source\":\"Mark\",\"message\":\"Markbuiltaroad\"},{\"source\":\"Mark\",\"message\":\"Markbuiltasettlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027sturnjustended\"},{\"source\":\"Pete\",\"message\":\"Petebuiltaroad\"},{\"source\":\"Pete\",\"message\":\"Petebuiltasettlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027sturnjustended\"},{\"source\":\"Brooke\",\"message\":\"Brookebuiltaroad\"},{\"source\":\"Brooke\",\"message\":\"Brookebuiltasettlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027sturnjustended\"},{\"source\":\"Sam\",\"message\":\"Sambuiltaroad\"},{\"source\":\"Sam\",\"message\":\"Sambuiltasettlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027sturnjustended\"},{\"source\":\"Sam\",\"message\":\"Samrolleda3.\"},{\"source\":\"Sam\",\"message\":\"Sambuiltaroad\"},{\"source\":\"Sam\",\"message\":\"Sambuiltaroad\"},{\"source\":\"Sam\",\"message\":\"Sambuiltaroad\"},{\"source\":\"Sam\",\"message\":\"Sambuiltaroad\"},{\"source\":\"Sam\",\"message\":\"Sambuiltaroad\"},{\"source\":\"Sam\",\"message\":\"Samupgradedtoacity\"},{\"source\":\"Sam\",\"message\":\"Sambuiltasettlement\"},{\"source\":\"Sam\",\"message\":\"Samupgradedtoacity\"},{\"source\":\"Sam\",\"message\":\"SamboughtaDevelopmentCard.\"},{\"source\":\"Sam\",\"message\":\"SamboughtaDevelopmentCard.\"},{\"source\":\"Sam\",\"message\":\"SamboughtaDevelopmentCard.\"},{\"source\":\"Sam\",\"message\":\"SamboughtaDevelopmentCard.\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027sturnjustended\"},{\"source\":\"Brooke\",\"message\":\"Brookerolleda5.\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027sturnjustended\"},{\"source\":\"Pete\",\"message\":\"Peterolleda5.\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027sturnjustended\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027sturnjustended\"},{\"source\":\"Mark\",\"message\":\"Markrolleda5.\"},{\"source\":\"Mark\",\"message\":\"Markrolleda5.\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027sturnjustended\"},{\"source\":\"Sam\",\"message\":\"Samrolleda5.\"},{\"source\":\"Sam\",\"message\":\"SamboughtaDevelopmentCard.\"},{\"source\":\"Sam\",\"message\":\"SamboughtaDevelopmentCard.\"},{\"source\":\"Sam\",\"message\":\"Samusedasoldier\"},{\"source\":\"Sam\",\"message\":\"SammovedtherobberandrobbedPete.\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":4,\"wood\":9,\"sheep\":1,\"wheat\":7,\"ore\":2},\"turnTracker\":{\"status\":\"Playing\",\"currentTurn\":0},\"biggestArmy\":2,\"longestRoad\":0,\"winner\":-1}";
	private static final Gson gson = new Gson();

	/**
	 * Returns the <code>Cookie</code> object corresponding to a requesting client.
	 * 
	 * @param exchange - An instance of <code>HttpExchange</code> received by an <code>HttpHandler</code>.
	 * @return the <code>Cookie</code> object corresponding to the requesting client.
	 */
	public static Cookie getCookie(HttpExchange exchange) {
		Cookie cookie = null;
		try {
			String encodedCookieStr = getCookies(exchange).get("catan.user");
			String decodedCookieStr = URLDecoder.decode(encodedCookieStr, "UTF-8");
//			Server.println("Decoded cookie: \"" + decodedCookieStr + "\".");
			cookie = gson.fromJson(decodedCookieStr, Cookie.class);
		} catch (JsonSyntaxException | UnsupportedEncodingException e) {}
		return cookie;
	}

	/**
	 * Gets the cookies received with a client's request. Since all cookies should be contained in a single <b>Cookie</b> header, only the first such header is parsed.
	 * 
	 * @param exchange - An instance of <code>HttpExchange</code> received by an <code>HttpHandler</code>.
	 * @return A <code>Map</code> where the keys are cookie names and the values are cookie values. If the client did not send any cookies, an empty <code>Map</code> is returned.
	 */
	public static Map<String, String> getCookies(HttpExchange exchange) {
//		Server.println("Parsing cookies...");
		Map<String, String> cookies = new TreeMap<>();
		if (exchange != null) {
			String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");
			if (cookieStr != null) {
				cookies = decodeQueryString(cookieStr, "\\s*;\\s*", "\\s*=\\s*");
//				if (Server.isDebugEnabled()) {
//					for (Map.Entry<String, String> e : cookies.entrySet()) {
//						System.out.println("  \"" + e.getKey() + "\" = \"" + e.getValue() + "\".");
//					}
//				}
			}
		}
//		Server.println("Done.");
		return cookies;
	}

	/**
	 * Sets a cookie to be sent with the server's response, in addition to any existing <b>Set-Cookie</b> headers. If either of the name or value is empty or <code>null</code>, no cookie is set.
	 * 
	 * @param exchange - An instance of <code>HttpExchange</code> received by an <code>HttpHandler</code>.
	 * @param name - The name of the cookie.
	 * @param value - The value of the cookie.
	 */
	public static void addCookie(HttpExchange exchange, String name, String value) {
		if (exchange != null) {
			if (name != null && value != null && !name.isEmpty() && !value.isEmpty()) {
				String cookie = name + "=" + value + "; Path=/";
				exchange.getResponseHeaders().add("Set-Cookie", cookie);
//				Server.println("Cookie added: \"" + cookie + "\".");
			}
		}
	}

	/**
	 * Attempts to authorize a user. If a request contains cookies corresponding to a valid combination of username, password, and user ID, the user is authorized.
	 * 
	 * @param exchange - An instance of <code>HttpExchange</code> received by an <code>HttpHandler</code>.
	 * @return <code>true</code> if the user was authorized, <code>false</code> otherwise.
	 */
	public static boolean authorizeUser(HttpExchange exchange) {
		boolean authorized = false;
		Cookie cookie = getCookie(exchange);
		if (cookie != null) {
			String username = cookie.getUsername();
			String password = cookie.getPassword();
			int userID = cookie.getId();
			for (RegisteredUser user : RegisteredUsers.get().getUsers()) {
				if (user.getName().equals(username) && user.getPassword().equals(password) && user.getPlayerID() == userID) {
					authorized = true;
					break;
				}
			}
		}
		return authorized;
	}

	/**
	 * Checks if request type is correct. Sends bad request if not correct.
	 * 
	 * @param type
	 * @param exchange
	 * @return true if correct; false if incorrect
	 * @throws IOException
	 */
	public static boolean checkRequestMethod(String type, HttpExchange exchange) throws IOException {
		String requestMethod = exchange.getRequestMethod().toUpperCase();
		if (requestMethod.equals(type)) {
			return true;
		} else {
//			Server.println("  Bad request method for \"" + exchange.getRequestURI().getPath() + "\": \"" + requestMethod + "\".");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_METHOD);
			return false;
		}
	}

	/**
	 * Sends a sample game model in JSON format to a client and closes the connection.
	 * 
	 * @param exchange - An instance of <code>HttpExchange</code> received by an <code>HttpHandler</code>.
	 * @param responseCode - The HTTP response code to send to the client.
	 * @throws IOException If an I/O error occurs while communicating with the client.
	 */
	public static void sendSampleModel(HttpExchange exchange, int responseCode) throws IOException {
		if (exchange != null) {
			sendStringAsJSON(exchange, responseCode, SAMPLE_MODEL);
		}
	}

	/**
	 * Sends a file to a client and closes the connection. If the file is <code>null</code>, no response body is sent.
	 * 
	 * @param exchange - An instance of <code>HttpExchange</code> received by an <code>HttpHandler</code>.
	 * @param responseCode - The HTTP response code to send to the client.
	 * @param data - The file to send to the client.
	 * @throws IOException If an I/O error occurs while communicating with the client.
	 */
	public static void sendFile(HttpExchange exchange, int responseCode, File data) throws IOException {
		if (exchange != null) {
			if (data != null) {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(data));
				sendInputStream(exchange, responseCode, bis, data.length());
				bis.close();
			} else {
				sendEmptyBody(exchange, responseCode);
			}
		}
	}

	/**
	 * Sends an object to a client and closes the connection. If the object is <code>Serializable</code>, it is serialized and sent as JSON. (This serialization may not work correctly if the object is a generic type.) Otherwise, the value of the object's <code>toString()</code> method is sent. If the object is <code>null</code>, no response body is sent.
	 * 
	 * @param exchange - An instance of <code>HttpExchange</code> received by an <code>HttpHandler</code>.
	 * @param responseCode - The HTTP response code to send to the client.
	 * @param data - The object to send to the client.
	 * @throws IOException If an I/O error occurs while communicating with the client.
	 */
	public static void sendObject(HttpExchange exchange, int responseCode, Object data) throws IOException {
		if (exchange != null) {
			if (data == null) {
				sendEmptyBody(exchange, responseCode);
			} else if (Serializable.class.isInstance(data)) {
				String dataJSON = gson.toJson(data);
				sendStringAsJSON(exchange, responseCode, dataJSON);
			} else {
				sendString(exchange, responseCode, data.toString());
			}
		}
	}

	/**
	 * Sends a string to a client and closes the connection. If the string is <code>null</code>, no response body is sent.
	 * 
	 * @param exchange - An instance of <code>HttpExchange</code> received by an <code>HttpHandler</code>.
	 * @param responseCode - The HTTP response code to send to the client.
	 * @param data - The string to send to the client.
	 * @throws IOException If an I/O error occurs while communicating with the client.
	 */
	public static void sendString(HttpExchange exchange, int responseCode, String data) throws IOException {
		if (exchange != null) {
			if (data != null) {
				exchange.sendResponseHeaders(responseCode, data.length());
				exchange.getResponseBody().write(data.getBytes(StandardCharsets.UTF_8));
				exchange.getResponseBody().close();
			} else {
				sendEmptyBody(exchange, responseCode);
			}
		}
	}

	/**
	 * Sends a string to a client as JSON and closes the connection. If the string is <code>null</code>, no response body is sent.
	 * 
	 * @param exchange - An instance of <code>HttpExchange</code> received by an <code>HttpHandler</code>.
	 * @param responseCode - The HTTP response code to send to the client.
	 * @param data - The string to send to the client as JSON.
	 * @throws IOException If an I/O error occurs while communicating with the client.
	 */
	public static void sendStringAsJSON(HttpExchange exchange, int responseCode, String data) throws IOException {
		if (exchange != null) {
			exchange.getResponseHeaders().set("Content-Type", "application/json");
			sendString(exchange, responseCode, data);
		}
	}

	/**
	 * Sends a response with no body to a client.
	 * 
	 * @param exchange - An instance of <code>HttpExchange</code> received by an <code>HttpHandler</code>.
	 * @param responseCode - The HTTP response code to send to the client.
	 * @throws IOException If an I/O error occurs while communicating with the client.
	 */
	public static void sendEmptyBody(HttpExchange exchange, int responseCode) throws IOException {
		if (exchange != null) {
			exchange.sendResponseHeaders(responseCode, -1);
		}
	}

	/**
	 * Converts the contents of an input stream to a string. This method makes it easy to convert a client's request body into a string:
	 * 
	 * <pre>String requestBodyStr = HandlerUtils.inputStreamToString(httpExchange.getRequestBody());</pre>
	 * 
	 * @param is - The input stream to convert to a string.
	 * @return A string containing the contents of the input stream. If the input stream is <code>null</code>, an empty string is returned.
	 */
	public static String inputStreamToString(InputStream is) {
		String str = "";
		if (is != null) {
			Scanner scan = new Scanner(is);
			scan.useDelimiter("\\A");
			if (scan.hasNext()) {
				str = scan.next();
			}
			scan.close();
		}
		return str;
	}

	/**
	 * Decodes a query string into a map of key-value pairs. This method is useful for decoding form-encoded request bodies. It assumes a string of the following form:
	 * 
	 * <pre>key0=value0&key1=value1&key2=value2</pre>
	 * 
	 * @param str - The query string to decode.
	 * @return A <code>Map</code> containing the key-value pairs represented by the string.
	 */
	public static Map<String, String> decodeQueryString(String str) {
		return decodeQueryString(str, "&", "=");
	}

	public static Class<? extends Command> getCommandClassForEndpoint(String endpoint) {
		Class<? extends Command> commandClass;
		switch (endpoint) {
		case "/moves/sendChat":
			commandClass = SendChatCommand.class;
			break;
		case "/moves/rollNumber":
			commandClass = RollNumberCommand.class;
			break;
		case "/moves/robPlayer":
			commandClass = RobPlayerCommand.class;
			break;
		case "/moves/finishTurn":
			commandClass = FinishTurnCommand.class;
			break;
		case "/moves/buyDevCard":
			commandClass = BuyDevCardCommand.class;
			break;
		case "/moves/Year_of_Plenty":
			commandClass = YearOfPlentyCommand.class;
			break;
		case "/moves/Road_Building":
			commandClass = RoadBuildingCommand.class;
			break;
		case "/moves/Soldier":
			commandClass = SoldierCommand.class;
			break;
		case "/moves/Monopoly":
			commandClass = MonopolyCommand.class;
			break;
		case "/moves/Monument":
			commandClass = MonumentCommand.class;
			break;
		case "/moves/buildRoad":
			commandClass = BuildRoadCommand.class;
			break;
		case "/moves/buildSettlement":
			commandClass = BuildSettlementCommand.class;
			break;
		case "/moves/buildCity":
			commandClass = BuildCityCommand.class;
			break;
		case "/moves/offerTrade":
			commandClass = OfferTradeCommand.class;
			break;
		case "/moves/acceptTrade":
			commandClass = AcceptTradeCommand.class;
			break;
		case "/moves/maritimeTrade":
			commandClass = MaritimeTradeCommand.class;
			break;
		case "/moves/discardCards":
			commandClass = DiscardCardsCommand.class;
			break;
		default:
			commandClass = null;
		}
		return commandClass;
	}

	private static Map<String, String> decodeQueryString(String str, String paramSeparator, String keyValueSeparator) {
		Map<String, String> params = new TreeMap<>();
		Scanner pScan = new Scanner(str);
		pScan.useDelimiter(paramSeparator);
		Scanner kvScan;
		while (pScan.hasNext()) {
			String param = pScan.next();
			kvScan = new Scanner(param);
			kvScan.useDelimiter(keyValueSeparator);
			String key = kvScan.next();
			kvScan.skip(keyValueSeparator);
			kvScan.useDelimiter("\\A");
			String value = kvScan.next();
			kvScan.close();
			params.put(key, value);
		}
		pScan.close();
		return params;
	}

	private static void sendInputStream(HttpExchange exchange, int responseCode, InputStream data, long length) throws IOException {
		if (exchange != null) {
			if (data != null) {
//				Server.println("  Sending stream to client...");
				if (length == 0) {
					sendEmptyBody(exchange, responseCode);
				} else {
					boolean knownLength = length > 0;
					exchange.sendResponseHeaders(responseCode, knownLength ? length : 0);
					// Send the data in chunks
					int bytesToRead = (knownLength && length < BUFFER.length) ? (int) length : BUFFER.length;
					int bytesRead;
//					long bytesWritten = 0;
					long bytesRemaining = knownLength ? length : 0;
					while ((!knownLength || bytesRemaining > 0) && (bytesRead = data.read(BUFFER, 0, bytesToRead)) != -1) {
						exchange.getResponseBody().write(BUFFER, 0, bytesRead);
						if (knownLength) {
							bytesRemaining -= bytesRead;
							if (bytesRemaining < BUFFER.length) {
								bytesToRead = (int) bytesRemaining;
							}
						}
//						if (Server.isDebugEnabled()) {
//							bytesWritten += bytesRead;
//							if (knownLength) {
//								System.out.println("    " + bytesWritten + " / " + length + " bytes sent ( " + 100 * bytesWritten / (double) length + " % ).");
//							} else {
//								System.out.println("    " + bytesWritten + " / ? bytes sent ( ? % ).");
//							}
//						}
					}
					// Close the connection
					exchange.getResponseBody().close();
//					Server.println("  Done.");
				}
			} else {
				sendEmptyBody(exchange, responseCode);
			}
		}
	}
}
