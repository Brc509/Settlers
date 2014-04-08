package catan.server.factory;

import catan.server.persistence.PersistenceProvider;
import catan.server.persistence.SQLitePlugin;

public class SQLitePluginFactory implements PersistenceProviderFactory {

	@Override
	public PersistenceProvider createInstance() {
		return new SQLitePlugin();
	}
}
