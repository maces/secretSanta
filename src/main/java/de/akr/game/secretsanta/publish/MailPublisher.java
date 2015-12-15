package de.akr.game.secretsanta.publish;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by akr on 11.12.15.
 */
public class MailPublisher implements SecretPublisher {

    private String server;
    private String port;
    private String username;
    private String password;
    private String senderAddress;

    public MailPublisher(final String server,
                         final String port,
                         final String username,
                         final String password,
                         final String senderAddress) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
        this.senderAddress = senderAddress;
    }

    @Override
    public void publish(final String participant,
                        final String title,
                        final String content,
                        final Object... contentArgs) {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        //properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, null);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(senderAddress));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(participant));

            // Set Subject: header field
            message.setSubject(title);

            // Now set the actual message
            message.setText(String.format(content, contentArgs));

            Transport t = session.getTransport("smtp");
            t.connect(server, username, password);
            // Send message
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            System.out.println("Sent message successfully....");
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }
}
