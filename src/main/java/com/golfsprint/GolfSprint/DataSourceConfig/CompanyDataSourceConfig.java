package com.golfsprint.GolfSprint.DataSourceConfig;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(basePackages = "com.golfsprint.GolfSprint.Company",
        entityManagerFactoryRef = "companyEntityManagerFactory",
        transactionManagerRef = "companyTransactionManager")
public class CompanyDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.company")
    public DataSourceProperties companyDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean(name="Company")
    @Primary
    @ConfigurationProperties("spring.datasource.company.configuration")
    public DataSource companyDataSource(){
        return companyDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
    @Bean(name = "companyEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean companyLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("Company") DataSource dataSource){
        Map<String, Object> jpaProperties = new HashMap<>();

        // Set your Hibernate and JPA properties here
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update"); //create-drop
       return entityManagerFactoryBuilder
               .dataSource(dataSource)
               .packages("com.golfsprint.GolfSprint.Company","com.golfsprint.GolfSprint.Token","com.golfsprint.GolfSprint.User", "com.golfsprint.GolfSprint.Reservation","com.golfsprint.GolfSprint.Course")
               .properties(jpaProperties)
               .build();
    }
    @Bean(name="companyTransactionManager")
    @Primary
    public PlatformTransactionManager companyPlatformTransactionManager(final @Qualifier("companyEntityManagerFactory")EntityManagerFactory entityManagerFactory){
       return new JpaTransactionManager(entityManagerFactory);
    }


}
