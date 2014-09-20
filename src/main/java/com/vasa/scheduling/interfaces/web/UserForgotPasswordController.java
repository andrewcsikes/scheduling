package com.vasa.scheduling.interfaces.web;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/user/forgotpassword")
@Controller
public class UserForgotPasswordController extends DefaultHandlerController{
	
	@Autowired
	private UserService mr;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(Model model, HttpServletRequest request) {
		
		return "user/forgotpassword";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		String username = request.getParameter("username");
		User user = mr.findByUserName(username);
		
		if(user==null){
			model.addAttribute("loginerror", "Username not valid.");
		}else{
			String emailAddress = user.getEmailAddress();
			String message = "You are receiving this email because someone submitted the forgot password form on the VASA Scheduling website. Your username is "+user.getUserName()+" and your password is "+user.getPassword();
			try{
				sendEmail(emailAddress, message);
				model.addAttribute("loginerror", "Your Password has been emailed to "+emailAddress);
			}catch(Exception e){
				model.addAttribute("loginerror", e.getCause() +": "+e.getMessage());
			}
		}
		
				
		return "login";
	}
	
	private void sendEmail(String emailAddress, String emailMessage) throws AddressException, MessagingException {
		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", "smtp");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(session);

		// Set From: header field of the header.
		message.setFrom(new InternetAddress("scheduling@vasayouthsports.com"));

		// Set To: header field of the header.
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				emailAddress));

		// Set Subject: header field
		message.setSubject("VASA Field Scheduling - Forgot Password");

		// Now set the actual message
		message.setText(emailMessage);

		// Send message
		Transport.send(message);
		//System.out.println("Sent message successfully....");
	}
	
}
