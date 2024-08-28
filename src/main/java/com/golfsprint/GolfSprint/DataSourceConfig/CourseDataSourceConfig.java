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
@EnableJpaRepositories(basePackages = "com.golfsprint.GolfSprint.Course",
        entityManagerFactoryRef = "courseEntityManagerFactory",
        transactionManagerRef = "courseTransactionManager")
public class CourseDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.course")
    public DataSourceProperties courseDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean(name="Course")
    @ConfigurationProperties("spring.datasource.course.configuration")
    public DataSource courseDataSource(){
        return courseDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
    @Bean(name = "courseEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean courseLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("Course") DataSource dataSource){
        Map<String, Object> jpaProperties = new HashMap<>();

        // Set your Hibernate and JPA properties here
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.golfsprint.GolfSprint.User","com.golfsprint.GolfSprint.Company","com.golfsprint.GolfSprint.Course")
                .properties(jpaProperties)
                .build();
    }
    @Bean(name="courseTransactionManager")
    public PlatformTransactionManager coursePlatformTransactionManager(final @Qualifier("courseEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
