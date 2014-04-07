package catan.server.handler;

import catan.server.handler.game.GameAddAIHandler;
import catan.server.handler.game.GameAddAIHandler_Prod;
import catan.server.handler.game.GameCommandsHandler;
import catan.server.handler.game.GameCommandsHandler_Prod;
import catan.server.handler.game.GameListAIHandler;
import catan.server.handler.game.GameListAIHandler_Prod;
import catan.server.handler.game.GameModelHandler;
import catan.server.handler.game.GameModelHandler_Prod;
import catan.server.handler.game.GameResetHandler;
import catan.server.handler.game.GameResetHandler_Prod;
import catan.server.handler.user.UserLoginHandler;
import catan.server.handler.user.UserLoginHandler_Prod;
import catan.server.handler.user.UserRegisterHandler;
import catan.server.handler.user.UserRegisterHandler_Prod;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class HandlerModule_Prod extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(FileDownloadHandler.class, FileDownloadHandler_Prod.class).build(FileDownloadHandlerFactory.class));
		bind(GameAddAIHandler.class).to(GameAddAIHandler_Prod.class);
		bind(GameCommandsHandler.class).to(GameCommandsHandler_Prod.class);
		bind(GameListAIHandler.class).to(GameListAIHandler_Prod.class);
		bind(GameModelHandler.class).to(GameModelHandler_Prod.class);
		bind(GameResetHandler.class).to(GameResetHandler_Prod.class);
		bind(UserLoginHandler.class).to(UserLoginHandler_Prod.class);
		bind(UserRegisterHandler.class).to(UserRegisterHandler_Prod.class);
		bind(UtilChangeLogLevelHandler.class).to(UtilChangeLogLevelHandler_Prod.class);
	}
}
