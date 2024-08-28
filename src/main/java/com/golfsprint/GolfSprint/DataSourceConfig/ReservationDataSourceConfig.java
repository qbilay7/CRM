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
@EnableJpaRepositories(basePackages = "com.golfsprint.GolfSprint.Reservation",
        entityManagerFactoryRef = "reservationEntityManagerFactory",
        transactionManagerRef = "reservationTransactionManager")
public class ReservationDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.reservation")
    public DataSourceProperties reservationDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean(name="Reservation")
    @ConfigurationProperties("spring.datasource.reservation.configuration")
    public DataSource reservationDataSource(){
        return reservationDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
    @Bean(name = "reservationEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean reservationLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("Reservation") DataSource dataSource){
        Map<String, Object> jpaProperties = new HashMap<>();

        // Set your Hibernate and JPA properties here
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.golfsprint.GolfSprint.Reservation","com.golfsprint.GolfSprint.User","com.golfsprint.GolfSprint.Company")
                .properties(jpaProperties)
                .build();
    }
    @Bean(name="reservationTransactionManager")
    public PlatformTransactionManager reservationPlatformTransactionManager(final @Qualifier("reservationEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
