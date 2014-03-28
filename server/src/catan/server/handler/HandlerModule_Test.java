package catan.server.handler;

import catan.server.handler.game.GameAddAIHandler;
import catan.server.handler.game.GameAddAIHandler_Test;
import catan.server.handler.game.GameCommandsHandler;
import catan.server.handler.game.GameCommandsHandler_Test;
import catan.server.handler.game.GameListAIHandler;
import catan.server.handler.game.GameListAIHandler_Test;
import catan.server.handler.game.GameModelHandler;
import catan.server.handler.game.GameModelHandler_Test;
import catan.server.handler.game.GameResetHandler;
import catan.server.handler.game.GameResetHandler_Test;
import catan.server.handler.user.UserLoginHandler;
import catan.server.handler.user.UserLoginHandler_Test;
import catan.server.handler.user.UserRegisterHandler;
import catan.server.handler.user.UserRegisterHandler_Test;
import catan.z.oldCode.server.handler.games.GamesCreateHandler;
import catan.z.oldCode.server.handler.games.GamesCreateHandler_Test;
import catan.z.oldCode.server.handler.games.GamesJoinHandler;
import catan.z.oldCode.server.handler.games.GamesJoinHandler_Test;
import catan.z.oldCode.server.handler.games.GamesListHandler;
import catan.z.oldCode.server.handler.games.GamesListHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesAcceptTradeHandler;
import catan.z.oldCode.server.handler.moves.MovesAcceptTradeHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesBuildCityHandler;
import catan.z.oldCode.server.handler.moves.MovesBuildCityHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesBuildRoadHandler;
import catan.z.oldCode.server.handler.moves.MovesBuildRoadHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesBuildSettlementHandler;
import catan.z.oldCode.server.handler.moves.MovesBuildSettlementHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesBuyDevCardHandler;
import catan.z.oldCode.server.handler.moves.MovesBuyDevCardHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesDiscardCardsHandler;
import catan.z.oldCode.server.handler.moves.MovesDiscardCardsHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesFinishTurnHandler;
import catan.z.oldCode.server.handler.moves.MovesFinishTurnHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesMaritimeTradeHandler;
import catan.z.oldCode.server.handler.moves.MovesMaritimeTradeHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesMonopolyHandler;
import catan.z.oldCode.server.handler.moves.MovesMonopolyHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesMonumentHandler;
import catan.z.oldCode.server.handler.moves.MovesMonumentHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesOfferTradeHandler;
import catan.z.oldCode.server.handler.moves.MovesOfferTradeHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesRoadBuildingHandler;
import catan.z.oldCode.server.handler.moves.MovesRoadBuildingHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesRobPlayerHandler;
import catan.z.oldCode.server.handler.moves.MovesRobPlayerHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesRollNumberHandler;
import catan.z.oldCode.server.handler.moves.MovesRollNumberHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesSendChatHandler;
import catan.z.oldCode.server.handler.moves.MovesSendChatHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesSoldierHandler;
import catan.z.oldCode.server.handler.moves.MovesSoldierHandler_Test;
import catan.z.oldCode.server.handler.moves.MovesYearOfPlentyHandler;
import catan.z.oldCode.server.handler.moves.MovesYearOfPlentyHandler_Test;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class HandlerModule_Test extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(FileDownloadHandler.class, FileDownloadHandler_Prod.class).build(FileDownloadHandlerFactory.class));
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
