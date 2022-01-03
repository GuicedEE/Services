package org.apache.logging.log4j;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.*;
import org.apache.logging.log4j.core.util.*;

import java.io.*;
import java.util.*;


public class Log4j2Utils
{
	
	public static Logger getLogger(String name)
	{
		return LogManager.getLogger(name);
	}
	
	public static Logger createLog4j2RollingLog(String configName, String logName,String rollingFilename, Level level)
	{
		return createLog4j2RollingLog(configName, logName,"%d %p %c [%t] %m%n",rollingFilename, level);
	}
	
	public static Logger createLog4j2RollingLog(String configName, String logName,String pattern,  String rollingFilename, Level level)
	{
		try
		{
			FileUtils.mkdir(new File("logs/"), true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
		
		builder.setStatusLevel(level);
		builder.setConfigurationName(configName);
// specifying the pattern layout
		LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout")
		                                              .addAttribute("pattern", pattern);
// specifying the policy for rolling file
		ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
		                                           .addComponent(builder.newComponent("CronTriggeringPolicy")
		                                                                .addAttribute("schedule", "0 0 0 * * ?"))
		                                           .addComponent(builder.newComponent("SizeBasedTriggeringPolicy")
		                                                                .addAttribute("size", "150M"));
		RootLoggerComponentBuilder rootLogger = builder.newRootLogger(level);

// create a console appender
		AppenderComponentBuilder appenderBuilder = builder.newAppender(logName, "RollingFile")
		                                                  .addAttribute("fileName", "logs/" + logName + ".log")
		                                                  .addAttribute("filePattern", "logs/$${date:yyyy-MM}/" + logName + "-%d{yyyy-MM-dd-HH-mm-ss}.log.gz")
		                                                  .add(layoutBuilder)
		                                                  .addComponent(triggeringPolicy);
		
		builder.add(appenderBuilder);
		rootLogger.add(builder.newAppenderRef(logName));
		builder.add(rootLogger);
		Configurator.reconfigure(builder.build());
		return  LogManager.getLogger(logName);
	}
	
}
