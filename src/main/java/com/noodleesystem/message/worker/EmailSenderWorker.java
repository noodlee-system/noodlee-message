package com.noodleesystem.message.worker;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noodleesystem.message.config.EmailClientConfig;
import com.noodleesystem.message.model.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
    private EmailClientConfig config;


    public EmailSenderWorker() {
        this.mapper = new ObjectMapper();
        config = new EmailClientConfig();
    }

    @RabbitListener(queues = "emails_queue")
    public void receive(String emailJsonString) {
        try {
            Email email = mapper.readValue(emailJsonString, Email.class);
        } catch (JsonParseException e) {
            System.err.println("JSON parsing error while trying to parse Email.");
        } catch (JsonMappingException e) {
            System.err.println("JSON mapping error while trying to parse Email.");
        } catch (IOException e) {
            System.err.println("IOException while trying to parse Email.");
        }
    }

    private void send(Email email){
        Session session = Session.getInstance(config.getProperties(), config.getAuthenticator());
        prepareMimeAndSend(email, session);
    }

    private void prepareMimeAndSend(Email email, Session session){
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

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
