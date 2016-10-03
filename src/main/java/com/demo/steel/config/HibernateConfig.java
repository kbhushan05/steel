package com.demo.steel.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
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
	
	@Bean
	public DataSource driverManagerDataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        return dataSource;
	}
	
	@Bean
	public DataSource c3p0DataSource(){
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(env.getRequiredProperty("jdbc.driverClassName"));
			dataSource.setJdbcUrl(env.getRequiredProperty("jdbc.url"));
			dataSource.setUser(env.getRequiredProperty("jdbc.username"));
			dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
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
        prop.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        prop.put("hibernate.connection.provider_class", env.getRequiredProperty("hibernate.connection.provider_class"));
        prop.put("hibernate.c3p0.min_size", env.getRequiredProperty("hibernate.c3p0.min_size"));
        prop.put("hibernate.c3p0.max_size", env.getRequiredProperty("hibernate.c3p0.max_size"));
        prop.put("hibernate.c3p0.timeout", env.getRequiredProperty("hibernate.c3p0.timeout"));
        prop.put("hibernate.c3p0.max_statements", env.getRequiredProperty("hibernate.c3p0.max_statements"));
        prop.put("hibernate.connection.driver_class", env.getRequiredProperty("jdbc.driverClassName"));
        prop.put("hibernate.connection.url", env.getRequiredProperty("jdbc.url"));
        prop.put("hibernate.connection.username", env.getRequiredProperty("jdbc.username"));
        prop.put("hibernate.connection.password", env.getRequiredProperty("jdbc.password"));
        return prop;
	}
	
	@Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
}
