package com.noodleesystem.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import serilogj.Log;
import serilogj.LoggerConfiguration;

import static serilogj.sinks.coloredconsole.ColoredConsoleSinkConfigurator.coloredConsole;
import static serilogj.sinks.seq.SeqSinkConfigurator.seq;

@Configuration
public class SerilogConfig {
    @Autowired
    Environment env;

    @Bean()
    public String setSerilogConfiguration() {
        final String serverUrl = env.getProperty("seq.server.url");

        Log.setLogger(new LoggerConfiguration()
                .writeTo(coloredConsole())
                .writeTo(seq(serverUrl))
                .createLogger());

        return serverUrl;
    }
}
