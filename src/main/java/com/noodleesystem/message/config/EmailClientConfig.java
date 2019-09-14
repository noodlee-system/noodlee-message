package com.noodleesystem.message.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class EmailClientConfig {
    private String host;
    private String from;
    private String login;
    private String password;
    private int port;
    private boolean useAuth;
    private boolean useStartTLS;
    @JsonIgnore
    private Properties properties;
    @JsonIgnore
    private Authenticator authenticator;

    private EmailClientConfig() {
    }

    public EmailClientConfig(String filePath) throws IOException {
        deserializeFromXML(filePath);

        createProperties();
        createAuthenticator();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isUseAuth() { return this.useAuth; }

    public void setUseAuth(boolean useAuth) {
        this.useAuth = useAuth;
    }

    public boolean isUseStartTLS() {
        return useStartTLS;
    }

    public void setUseStartTLS(boolean useStartTLS) {
        this.useStartTLS = useStartTLS;
    }

    public Properties getProperties() {
        return properties;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void serializeToXML(String filePath) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File(filePath), this);
    }

    private void deserializeFromXML(String filePath) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        EmailClientConfig tempConfig = xmlMapper.readValue(content, EmailClientConfig.class);
        this.host = tempConfig.host;
        this.from = tempConfig.from;
        this.login = tempConfig.login;
        this.password = tempConfig.password;
        this.port = tempConfig.port;
        this.useAuth = tempConfig.useAuth;
        this.useStartTLS = tempConfig.useStartTLS;
    }

    private void createProperties() {
        properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", useAuth);
        properties.put("mail.smtp.starttls.enable", useStartTLS);
    }

    private void createAuthenticator() {
        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        };
    }
}
