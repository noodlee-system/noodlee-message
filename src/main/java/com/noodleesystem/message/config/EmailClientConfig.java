package com.noodleesystem.message.config;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

public class EmailClientConfig {
    private final String host;
    private final String from;
    private final String password;
    private int port;
    private boolean useAuth;
    private boolean useStarttls;
    private Properties properties;
    private Authenticator authenticator;

    public EmailClientConfig(){
        host = "mail.thomas-it.pl";
        from = "projektpp@thomas-it.pl";
        password = "ppjestsuper1234";
        port = 587;
        useAuth = true;
        useStarttls = true;

        properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", useAuth);
        properties.put("mail.smtp.starttls.enable", useStarttls);

        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
    }

    public Properties getProperties() {
        return properties;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public String getFrom() {
        return from;
    }
}
