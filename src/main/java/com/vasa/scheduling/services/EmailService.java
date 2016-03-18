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

import com.vasa.scheduling.enums.Carrier;

@Service("emailService")
public class EmailService {

	public void sendEmail(String toAddress, String fromAddress, String subject, String emailMessage, String type) throws AddressException, MessagingException {
		send(toAddress, fromAddress, subject, emailMessage, type);
	}
	
	public void sendEmail(String emailAddress, String subject, String emailMessage, String type) throws AddressException, MessagingException {
		send(emailAddress, "scheduling@vasayouthsports.com", subject, emailMessage, type);
	}
	
	public void send(String emailAddress, String fromAddress, String subject, String emailMessage, String type) throws AddressException, MessagingException {
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
        bodyPart.setContent(emailMessage, type);
        multipart.addBodyPart(bodyPart);
 
        message.setContent(multipart);
        message.setFrom(new InternetAddress(fromAddress));
        message.setSubject("VASA Field Scheduling - "+subject);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
 
        transport.connect();
        transport.sendMessage(message,
        message.getRecipients(Message.RecipientType.TO));
        transport.close();
	}
	
	private void sendText(String emailAddress, String fromAddress, String subject, String emailMessage) throws AddressException, MessagingException {
		Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.sendgrid.net");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
 
        javax.mail.Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        Transport transport = mailSession.getTransport();
        
        javax.mail.Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(fromAddress));
        message.setSubject("VASA Field Scheduling - "+subject);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));

        message.setContent(emailMessage, "text/plain");
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
	
	public static String convertToEmail(String phone, Carrier carrier){
		String email = null;
		
		if(carrier == null){
			email = null;
		}else if(carrier.equals(Carrier.ATT)){
			email = phone + "@txt.att.net";
		}else if(carrier.equals(Carrier.VERIZON)){
			email = phone + "@vtext.com";
		}else if(carrier.equals(Carrier.SPRINT)){
			email = phone + "@messaging.sprintpcs.com";
		}else if(carrier.equals(Carrier.TMOBIL)){
			email = phone + "@tmomail.net";
		}else if(carrier.equals(Carrier.NEXTTEL)){
			email = phone + "@messaging.nextel.com";
		}else if(carrier.equals(Carrier.CRICKET)){
			email = phone + "@mms.mycricket.com";
		}
		
		return email;
		
	}
	
	public static void main(String[] args){
		EmailService es = new EmailService();
		try {
			es.sendText("9038213425@txt.att.net", "scheduling@vasayouthsports.com", "New Slot Available", "The practice spot for FM South at Sat, Mar 19 09:30 PM was previously scheduled, but is now available.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Sent");
	}

}
