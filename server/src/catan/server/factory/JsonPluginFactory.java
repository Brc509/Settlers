package catan.server.factory;

import catan.server.persistence.JsonPlugin;
import catan.server.persistence.PersistenceProvider;

public class JsonPluginFactory implements PersistenceProviderFactory {

	@Override
	public PersistenceProvider createInstance() {
		return new JsonPlugin();
	}
}
