package catan.server.handler;

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
import catan.server.handler.user.UserLoginHandler;
import catan.server.handler.user.UserRegisterHandler;

import com.google.inject.AbstractModule;

public class HandlerModule_Test extends AbstractModule {

	@Override
	protected void configure() {
		bind(FileDownloadHandler.class).to(FileDownloadHandler_Test.class);
		bind(GameAddAIHandler.class).to(GameAddAIHandler_Test.class);
		bind(GameCommandsHandler.class).to(GameCommandsHandler_Test.class);
		bind(GameListAIHandler.class).to(GameListAIHandler_Test.class);
		bind(GameModelHandler.class).to(GameModelHandler_Test.class);
		bind(GameResetHandler.class).to(GameResetHandler_Test.class);
		bind(GamesCreateHandler.class).to(GamesCreateHandler_Test.class);
		bind(GamesJoinHandler.class).to(GamesJoinHandler_Test.class);
		bind(GamesListHandler.class).to(GamesListHandler_Test.class);
		bind(MovesAcceptTradeHandler.class).to(MovesAcceptTradeHandler_Test.class);
		bind(MovesBuildCityHandler.class).to(MovesBuildCityHandler_Test.class);
		bind(MovesBuildRoadHandler.class).to(MovesBuildRoadHandler_Test.class);
		bind(MovesBuildSettlementHandler.class).to(MovesBuildSettlementHandler_Test.class);
		bind(MovesBuyDevCardHandler.class).to(MovesBuyDevCardHandler_Test.class);
		bind(MovesDiscardCardsHandler.class).to(MovesDiscardCardsHandler_Test.class);
		bind(MovesFinishTurnHandler.class).to(MovesFinishTurnHandler_Test.class);
		bind(MovesMaritimeTradeHandler.class).to(MovesMaritimeTradeHandler_Test.class);
		bind(MovesMonopolyHandler.class).to(MovesMonopolyHandler_Test.class);
		bind(MovesMonumentHandler.class).to(MovesMonumentHandler_Test.class);
		bind(MovesOfferTradeHandler.class).to(MovesOfferTradeHandler_Test.class);
		bind(MovesRoadBuildingHandler.class).to(MovesRoadBuildingHandler_Test.class);
		bind(MovesRobPlayerHandler.class).to(MovesRobPlayerHandler_Test.class);
		bind(MovesRollNumberHandler.class).to(MovesRollNumberHandler_Test.class);
		bind(MovesSendChatHandler.class).to(MovesSendChatHandler_Test.class);
		bind(MovesSoldierHandler.class).to(MovesSoldierHandler_Test.class);
		bind(MovesYearOfPlentyHandler.class).to(MovesYearOfPlentyHandler_Test.class);
		bind(UserLoginHandler.class).to(UserLoginHandler_Test.class);
		bind(UserRegisterHandler.class).to(UserRegisterHandler_Test.class);
		bind(UtilChangeLogLevelHandler.class).to(UtilChangeLogLevelHandler_Test.class);
	}
}
