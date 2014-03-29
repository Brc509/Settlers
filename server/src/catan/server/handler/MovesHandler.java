package catan.server.handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import catan.model.Model;
import catan.server.Games;
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

// GSON
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MovesHandler implements HttpHandler {

	private Gson gson = new Gson();

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		int gameId = Integer.parseInt(HandlerUtils.getCookies(exchange).get("catan.game"));
		String endpoint = exchange.getRequestURI().getPath();
		String json = HandlerUtils.inputStreamToString(exchange.getRequestBody());
		String commandType = getCommandType(json);
		Class<? extends Command> commandClass = null;

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
			System.out.println("MovesHandler. commandType: " + commandType + ". Sent Bad Request");
			HandlerUtils.sendEmptyBody(exchange, HttpURLConnection.HTTP_BAD_REQUEST);
			return; //DON'T DO ANYTHING ELSE
		}

		Command c = gson.fromJson(json, commandClass);
		Object error = c.execute(gameId);

		//if (error) send error response
		//else { send back the model appropriate to the game }
		// TODO
		Model game = Games.get().getGames().get(gameId);
		HandlerUtils.sendStringAsJSON(exchange, HttpURLConnection.HTTP_OK, game.getModelJSON());
	}

	private String getCommandType(String json) {
		JsonObject command = gson.fromJson(json, JsonObject.class);
		String type = command.get("type").getAsString();
		System.out.println("MovesHandler:COMMAND TYPE = " + type);
		return type;
	}

}
