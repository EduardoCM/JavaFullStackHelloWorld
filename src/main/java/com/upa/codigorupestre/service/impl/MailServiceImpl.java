package com.upa.codigorupestre.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.upa.codigorupestre.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendMailConfirmAccount(String email, String fullName, String planet) {
		log.info("Enviando mail {} al usuario {} del planera {}", email, fullName, planet);
		
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			message.setFrom(new InternetAddress("codigorupestre@hotmail.com"));
			message.setRecipients(MimeMessage.RecipientType.TO, email);
			message.setContent(getHtmlFile(fullName, planet), "text/html; charset=utf-8");
			message.setSubject("Confirm your account to Space Travels");
			
			 mailSender.send(message);
			
			
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String getHtml(String fullName, String planet) {
		String htmlEmail = "<html> <body> <h1> Welcome " + fullName + " planet: " + planet  + " </h1> </body> </html>";
		return htmlEmail;
	}
	
	private String getHtmlFile(String fullName, String planet) {
		String htmlEmail = null;
		
		try {
			var file = Files.lines(Paths.get("src/main/resources/mail/welcomeMail.html"));
			
			var html = file.collect(Collectors.joining());
			
			htmlEmail = html.replace("{{fullName}}", fullName).replace("{{planet}}", planet);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return htmlEmail;
	}
	
	

}
