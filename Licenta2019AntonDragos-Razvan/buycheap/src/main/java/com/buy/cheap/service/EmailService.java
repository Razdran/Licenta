package com.buy.cheap.service;

import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@Service
public class EmailService {
    public void send(final String sender, String receiver,
                            final String password, String subject, String messageToSend){

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender,password);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(receiver));
            message.setSubject(subject);
            message.setText(messageToSend);

            Transport.send(message);

        } catch (MessagingException e) {throw new RuntimeException(e);}

    }
}
