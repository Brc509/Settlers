package catan.server.factory;

import catan.server.persistence.PersistenceProvider;

public interface PersistenceProviderFactory {

	public PersistenceProvider createInstance();
}
