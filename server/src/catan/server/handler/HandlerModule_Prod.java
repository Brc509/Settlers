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

public class HandlerModule_Prod extends AbstractModule {

	@Override
	protected void configure() {
		bind(FileDownloadHandler.class).to(FileDownloadHandler_Prod.class);
		bind(GameAddAIHandler.class).to(GameAddAIHandler_Prod.class);
		bind(GameCommandsHandler.class).to(GameCommandsHandler_Prod.class);
		bind(GameListAIHandler.class).to(GameListAIHandler_Prod.class);
		bind(GameModelHandler.class).to(GameModelHandler_Prod.class);
		bind(GameResetHandler.class).to(GameResetHandler_Prod.class);
		bind(GamesCreateHandler.class).to(GamesCreateHandler_Prod.class);
		bind(GamesJoinHandler.class).to(GamesJoinHandler_Prod.class);
		bind(GamesListHandler.class).to(GamesListHandler_Prod.class);
		bind(MovesAcceptTradeHandler.class).to(MovesAcceptTradeHandler_Prod.class);
		bind(MovesBuildCityHandler.class).to(MovesBuildCityHandler_Prod.class);
		bind(MovesBuildRoadHandler.class).to(MovesBuildRoadHandler_Prod.class);
		bind(MovesBuildSettlementHandler.class).to(MovesBuildSettlementHandler_Prod.class);
		bind(MovesBuyDevCardHandler.class).to(MovesBuyDevCardHandler_Prod.class);
		bind(MovesDiscardCardsHandler.class).to(MovesDiscardCardsHandler_Prod.class);
		bind(MovesFinishTurnHandler.class).to(MovesFinishTurnHandler_Prod.class);
		bind(MovesMaritimeTradeHandler.class).to(MovesMaritimeTradeHandler_Prod.class);
		bind(MovesMonopolyHandler.class).to(MovesMonopolyHandler_Prod.class);
		bind(MovesMonumentHandler.class).to(MovesMonumentHandler_Prod.class);
		bind(MovesOfferTradeHandler.class).to(MovesOfferTradeHandler_Prod.class);
		bind(MovesRoadBuildingHandler.class).to(MovesRoadBuildingHandler_Prod.class);
		bind(MovesRobPlayerHandler.class).to(MovesRobPlayerHandler_Prod.class);
		bind(MovesRollNumberHandler.class).to(MovesRollNumberHandler_Prod.class);
		bind(MovesSendChatHandler.class).to(MovesSendChatHandler_Prod.class);
		bind(MovesSoldierHandler.class).to(MovesSoldierHandler_Prod.class);
		bind(MovesYearOfPlentyHandler.class).to(MovesYearOfPlentyHandler_Prod.class);
		bind(UserLoginHandler.class).to(UserLoginHandler_Prod.class);
		bind(UserRegisterHandler.class).to(UserRegisterHandler_Prod.class);
		bind(UtilChangeLogLevelHandler.class).to(UtilChangeLogLevelHandler_Prod.class);
	}
}
