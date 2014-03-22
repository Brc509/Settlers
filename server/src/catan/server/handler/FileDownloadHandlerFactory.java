package catan.server.handler;

import com.google.inject.assistedinject.Assisted;

public interface FileDownloadHandlerFactory {

	FileDownloadHandler create(@Assisted("context") String context, @Assisted("root") String root);
}
