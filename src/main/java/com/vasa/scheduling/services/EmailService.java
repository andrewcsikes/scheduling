package com.vasa.scheduling.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService {

	public void sendEmail(String emailAddress, String emailMessage) throws AddressException, MessagingException {
//		// Get system properties
//		Properties properties = System.getProperties();
//
//		// Setup mail server
//		properties.setProperty("mail.smtp.host", "smtp");
//
//		// Get the default Session object.
//		Session session = Session.getDefaultInstance(properties);
//
//		// Create a default MimeMessage object.
//		MimeMessage message = new MimeMessage(session);
//
//		// Set From: header field of the header.
//		message.setFrom(new InternetAddress("scheduling@vasayouthsports.com"));
//
//		// Set To: header field of the header.
//		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
//				emailAddress));
//
//		// Set Subject: header field
//		message.setSubject("VASA Field Scheduling - Forgot Password");
//
//		// Now set the actual message
//		message.setText(emailMessage);
//
//		// Send message
//		Transport.send(message);
//		//System.out.println("Sent message successfully....");
		
		

		Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.sendgrid.net");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
 
        javax.mail.Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
        // mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();
 
        MimeMessage message = new MimeMessage(mailSession);
 
        javax.mail.Multipart multipart = new javax.mail.internet.MimeMultipart("alternative");
 
        javax.mail.BodyPart bodyPart = new javax.mail.internet.MimeBodyPart();
        bodyPart.setContent(emailMessage, "text/html");
        multipart.addBodyPart(bodyPart);
 
        message.setContent(multipart);
        message.setFrom(new InternetAddress("scheduling@vasayouthsports.com"));
        message.setSubject("VASA Field Scheduling - Forgot Password");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
 
        transport.connect();
        transport.sendMessage(message,
        message.getRecipients(Message.RecipientType.TO));
        transport.close();
	}
		
			 
	private static class SMTPAuthenticator extends javax.mail.Authenticator {
		public javax.mail.PasswordAuthentication getPasswordAuthentication() {
			String username = "aandrewcsikes263";
			String password = "flash23";
			return new javax.mail.PasswordAuthentication(username, password);
		}
	}

}
