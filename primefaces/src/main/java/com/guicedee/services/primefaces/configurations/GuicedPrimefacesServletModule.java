package com.guicedee.services.primefaces.configurations;

import com.guicedee.guicedservlets.services.GuiceSiteInjectorModule;
import com.guicedee.guicedservlets.services.IGuiceSiteBinder;
import com.guicedee.logger.LogFactory;

import java.util.logging.Logger;

public class GuicedPrimefacesServletModule
		implements IGuiceSiteBinder<GuiceSiteInjectorModule>
{
	private static final Logger log = LogFactory.getLog("GuicedPrimefacesServletModule");

	@Override
	public void onBind(GuiceSiteInjectorModule module)
	{
		module.bind(org.primefaces.webapp.FileUploadChunksServlet.class)
		      .in(com.google.inject.Singleton.class);

		module.serve$("/file/resume/")
		      .with(org.primefaces.webapp.FileUploadChunksServlet.class);

		log.config("Serving /file/resume/ with FileUploadChunksServlet");
	}
}
