package com.demo.steel.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailNotificationService {
	
	@Autowired
	private MailSender mailSender;
	@Autowired
	private SimpleMailMessage mailMessage;
	
	public void sendEmail(String to, String subject, String text){
		SimpleMailMessage tempMailMessage = new SimpleMailMessage(getMailMessage());
		tempMailMessage.setTo(to);
		tempMailMessage.setSubject(subject);
		tempMailMessage.setText(text+"\n ** system generated message do not reply.");
		tempMailMessage.setSentDate(new Date());
		mailSender.send(tempMailMessage);
	}
	
	public MailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	public SimpleMailMessage getMailMessage() {
		return mailMessage;
	}
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
	
}
