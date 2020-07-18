package com.guicedee.services.primefaces.configurations;

import com.guicedee.guicedservlets.undertow.services.UndertowDeploymentConfigurator;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.ListenerInfo;

public class UndertowServletConfig
		implements UndertowDeploymentConfigurator
{
	@Override
	public DeploymentInfo configure(DeploymentInfo deploymentInfo)
	{
		deploymentInfo.addListener(new ListenerInfo(org.primefaces.webapp.UploadedFileCleanerListener.class));
		return deploymentInfo;
	}
}
