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
@EnableJpaRepositories(basePackages = "com.golfsprint.GolfSprint.Delivery",
        entityManagerFactoryRef = "deliveryEntityManagerFactory",
        transactionManagerRef = "deliveryTransactionManager")
public class DeliveryDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.delivery")
    public DataSourceProperties deliveryDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean(name="Delivery")
    @ConfigurationProperties("spring.datasource.delivery.configuration")
    public DataSource deliveryDataSource(){
        return deliveryDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
    @Bean(name = "deliveryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean deliveryLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("Delivery") DataSource dataSource){
        Map<String, Object> jpaProperties = new HashMap<>();

        // Set your Hibernate and JPA properties here
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop"); //update
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.golfsprint.GolfSprint.Delivery","com.golfsprint.GolfSprint.Shop","com.golfsprint.GolfSprint.User","com.golfsprint.GolfSprint.Company")
                .properties(jpaProperties)
                .build();
    }
    @Bean(name="deliveryTransactionManager")
    public PlatformTransactionManager deliveryPlatformTransactionManager(final @Qualifier("deliveryEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
*/