package com.demo.steel.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:hibernate.properties","classpath:database.properties" })
public class HibernateConfig {

	@Autowired
	private Environment env;

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(c3p0DataSource());
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setPackagesToScan("com.demo.steel.domain","com.demo.steel.security.domain");
		return sessionFactory;
	}
	
	
	public String getUrl(){
		if(env.getRequiredProperty("database.context").equals("prod")){
			String url = "jdbc:mysql://";
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
			String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
			return url + host + ":" + port + "/steel";
		}else{
			return env.getRequiredProperty("database.url");
		}
	}
	
	@Bean
	public DataSource c3p0DataSource(){
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(env.getRequiredProperty("database.driver"));
			dataSource.setJdbcUrl(getUrl());
			dataSource.setUser(env.getRequiredProperty("database.username"));
			dataSource.setPassword(env.getRequiredProperty("database.password"));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	public Properties hibernateProperties(){
		Properties prop = new Properties();
        prop.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        prop.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        prop.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        prop.put("hibernate.connection.provider_class", env.getRequiredProperty("hibernate.connection.provider_class"));
        prop.put("hibernate.c3p0.min_size", env.getRequiredProperty("hibernate.c3p0.min_size"));
        prop.put("hibernate.c3p0.max_size", env.getRequiredProperty("hibernate.c3p0.max_size"));
        prop.put("hibernate.c3p0.timeout", env.getRequiredProperty("hibernate.c3p0.timeout"));
        prop.put("hibernate.c3p0.max_statements", env.getRequiredProperty("hibernate.c3p0.max_statements"));
        prop.put("hibernate.connection.driver_class", env.getRequiredProperty("database.driver"));
        prop.put("hibernate.connection.url", getUrl());
        prop.put("hibernate.connection.username", env.getRequiredProperty("database.username"));
        prop.put("hibernate.connection.password", env.getRequiredProperty("database.password"));
        return prop;
	}
	
	@Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
	
	@Bean
	public SpringLiquibase liquibase(){
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(c3p0DataSource());
		liquibase.setIgnoreClasspathPrefix(true);
		liquibase.setChangeLog("classpath:/liquibase/changelog-master.xml");
		liquibase.setContexts(env.getRequiredProperty("database.context"));
		return liquibase;
	}
}
