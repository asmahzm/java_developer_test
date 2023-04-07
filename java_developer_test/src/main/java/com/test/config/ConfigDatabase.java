package com.test.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "springEntityManager", 
		transactionManagerRef = "springTransactionManager", 
		basePackages = "com.test.repositories")
public class ConfigDatabase {

	@Bean(name = "springDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource springDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "springEntityManager")
	public LocalContainerEntityManagerFactoryBean springEntityManager(EntityManagerFactoryBuilder builder, 
			@Qualifier(value = "springDataSource") DataSource springDataSource) {
		return builder.dataSource(springDataSource).packages("com.test.entities").build();
	}
	
	@Bean(name = "springTransactionManager")
	public PlatformTransactionManager springTransactionManager(
			@Qualifier(value = "springEntityManager") EntityManagerFactory springEntityManager) {
		return new JpaTransactionManager(springEntityManager);
	}
	
}
