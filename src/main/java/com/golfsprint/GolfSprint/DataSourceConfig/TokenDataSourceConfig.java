package com.golfsprint.GolfSprint.DataSourceConfig;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.golfsprint.GolfSprint.Token",
        entityManagerFactoryRef = "tokenEntityManagerFactory",
        transactionManagerRef = "tokenTransactionManager")
public class TokenDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.token")
    public DataSourceProperties tokenDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean(name="Token")
    @ConfigurationProperties("spring.datasource.token.configuration")
    public DataSource tokenDataSource(){
        return tokenDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
    @Bean(name = "tokenEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tokenLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("Token") DataSource dataSource){
        Map<String, Object> jpaProperties = new HashMap<>();

        // Set your Hibernate and JPA properties here
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.golfsprint.GolfSprint.Token","com.golfsprint.GolfSprint.Company")
                .properties(jpaProperties)
                .build();
    }
    @Bean(name="tokenTransactionManager")
    public PlatformTransactionManager tokenPlatformTransactionManager(final @Qualifier("tokenEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
