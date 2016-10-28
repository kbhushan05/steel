package com.demo.steel.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource(value = { "classpath:smtp.properties" })
public class MailConfig {
	private static final Logger logger = LoggerFactory.getLogger(MailConfig.class);
			
	@Autowired
	private Environment env;
	
	@Bean
	public JavaMailSender getMailSender(){
		JavaMailSenderImpl javaMail = new JavaMailSenderImpl();
		
		javaMail.setHost(env.getRequiredProperty("mail.host"));
		javaMail.setPort(Integer.parseInt(env.getRequiredProperty("mail.port")));
		javaMail.setProtocol("smtp");
		javaMail.setUsername(env.getRequiredProperty("mail.username"));
		javaMail.setPassword(env.getRequiredProperty("mail.password"));
		
		javaMail.setJavaMailProperties(getJavaMailProperties());
		logger.debug("Created JavaMailSender with smtp://"+javaMail.getHost()+":"+javaMail.getPort());
		
		return javaMail;
	}
	
	private Properties getJavaMailProperties(){
		Properties props = new Properties();
		props.put("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
		props.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
		props.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
		props.put("mail.debug", env.getProperty("mail.debug"));
		
		return props;
	}
	@Bean
	public SimpleMailMessage getSimpleMailMessage(){
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(env.getRequiredProperty("mail.from"));
		return mailMessage;
	}
	
}
