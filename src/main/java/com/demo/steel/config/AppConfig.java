package com.demo.steel.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.demo.steel")
public class AppConfig {

	Logger logger = LoggerFactory.getLogger(AppConfig.class);
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	    resolver.setDefaultEncoding("utf-8");
	    resolver.setMaxInMemorySize(500000);
	    logger.debug("Created CommonsMultipartResolver with max file size 5MB and utf-8 encoding");
	    return resolver;
	}
	
	@Bean
	public MessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasename("error_messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	@Bean
	public LocaleResolver localeResolver(){
		AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
	    return resolver;
	}
	
}
