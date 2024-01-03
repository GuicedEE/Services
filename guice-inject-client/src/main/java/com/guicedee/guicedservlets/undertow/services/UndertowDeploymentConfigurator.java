package com.guicedee.guicedservlets.undertow.services;

import com.guicedee.guicedinjection.interfaces.IDefaultService;
import io.undertow.servlet.api.DeploymentInfo;

@FunctionalInterface
public interface UndertowDeploymentConfigurator extends IDefaultService<UndertowDeploymentConfigurator>
{
	DeploymentInfo configure(DeploymentInfo deploymentInfo);
}
