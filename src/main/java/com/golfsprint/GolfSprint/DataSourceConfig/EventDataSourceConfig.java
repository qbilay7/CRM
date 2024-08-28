/*package com.golfsprint.GolfSprint.DataSourceConfig;

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
@EnableJpaRepositories(basePackages = "com.golfsprint.GolfSprint.Events",
        entityManagerFactoryRef = "eventEntityManagerFactory",
        transactionManagerRef = "eventTransactionManager")
public class EventDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.event")
    public DataSourceProperties eventDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean(name="Event")
    @ConfigurationProperties("spring.datasource.event.configuration")
    public DataSource eventDataSource(){
        return eventDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
    @Bean(name = "eventEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean eventLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("Event") DataSource dataSource){
        Map<String, Object> jpaProperties = new HashMap<>();

        // Set your Hibernate and JPA properties here
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop"); //update
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.golfsprint.GolfSprint.Events","com.golfsprint.GolfSprint.Company")
                .properties(jpaProperties)
                .build();
    }
    @Bean(name="eventTransactionManager")
    public PlatformTransactionManager eventPlatformTransactionManager(final @Qualifier("eventEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
*/