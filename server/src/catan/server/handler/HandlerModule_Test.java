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
		bind(UserLoginHandler.class).to(UserLoginHandler_Test.class);
		bind(UserRegisterHandler.class).to(UserRegisterHandler_Test.class);
		bind(UtilChangeLogLevelHandler.class).to(UtilChangeLogLevelHandler_Test.class);
	}
}
