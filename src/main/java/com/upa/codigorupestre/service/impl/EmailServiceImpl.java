package com.upa.codigorupestre.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.upa.codigorupestre.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendMail(String email, String firstName, String lastName, String planet) {
		log.info("inifica envio de mail: {}", email);
		
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			message.setFrom(new InternetAddress(email));
			message.setRecipients(MimeMessage.RecipientType.TO, email);
			message.setContent(getHTMLFile(firstName, lastName, planet), "text/html; charset=utf-8");
			message.setSubject("Confirm my account to Space Travels");
			
			mailSender.send(message);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("Envio de forma exitosa");
	}

	private Object getHTMLString(String firstName, String lastName) {
		String htmlEmail = "<html> <body> <h1> Welcome " + firstName + " " + lastName + " </h1> </body> </html>";
		return htmlEmail;
	}
	
	private String getHTMLFile(String firstName, String lastName, String planet) {
		String htmlEmail = null;
		try {
			var file = Files.lines(Paths.get("src/main/resources/mail/welcomeMail.html"));
			
			var html = file.collect(Collectors.joining());
			
			String fullName = firstName + " " + lastName;
			
			htmlEmail = html.replace("{{fullName}}", fullName).replace("{{planet}}", planet);
			
			
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return htmlEmail;
	}
	
	

}
