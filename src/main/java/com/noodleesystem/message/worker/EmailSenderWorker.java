package com.noodleesystem.message.worker;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noodleesystem.message.config.EmailClientConfig;
import com.noodleesystem.message.model.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import serilogj.Log;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class EmailSenderWorker {
    private ObjectMapper mapper;
    private static EmailClientConfig config;

    public EmailSenderWorker() throws IOException {
        this.mapper = new ObjectMapper();
        config = new EmailClientConfig("email_config.xml");
    }

    @RabbitListener(queues = "emails_queue")
    public void receive(String emailJsonString) {
        try {
            Email email = mapper.readValue(emailJsonString, Email.class);
            Log.information("Email successfully parsed from JSON.");
            Log.information("Email: %s", emailJsonString);
            send(email);
        } catch (JsonParseException e) {
            Log.warning("JSON parsing error while trying to parse Email: %s", e.getMessage());
        } catch (JsonMappingException e) {
            Log.warning("JSON mapping error while trying to parse Email: %s", e.getMessage());
        } catch (IOException e) {
            Log.warning("IOException while trying to parse Email: %s", e.getMessage());
        }
    }

    private void send(Email email) {
        Session session = Session.getInstance(config.getProperties(), config.getAuthenticator());
        Log.information("Email session successfully established.");
        prepareMimeAndSend(email, session);
    }

    private void prepareMimeAndSend(Email email, Session session) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(config.getFrom(), "NoReply-Noodlee"));
            msg.setReplyTo(InternetAddress.parse(config.getFrom(), false));
            msg.setSubject(email.getSubcjet());
            msg.setText(email.getBody());
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo(), false));
            Transport.send(msg);
            Log.information("Email successfully sent.");
        } catch (MessagingException e) {
            Log.warning("MessagingException while trying to send Email: %s", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            Log.warning("UnsupportedEncodingException while trying to send Email: %s", e.getMessage());
        }
    }
}