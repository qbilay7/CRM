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
@EnableJpaRepositories(basePackages = "com.golfsprint.GolfSprint.CourseRequest",
        entityManagerFactoryRef = "course-requestEntityManagerFactory",
        transactionManagerRef = "course-requestTransactionManager")
public class CourseRequestDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.course-request")
    public DataSourceProperties courseRequestDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean(name="CourseRequest")
    @ConfigurationProperties("spring.datasource.course-request.configuration")
    public DataSource courseRequestDataSource(){
        return courseRequestDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
    @Bean(name = "course-requestEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean courseRequestLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("CourseRequest") DataSource dataSource){
        Map<String, Object> jpaProperties = new HashMap<>();

        // Set your Hibernate and JPA properties here
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.golfsprint.GolfSprint.User","com.golfsprint.GolfSprint.Company","com.golfsprint.GolfSprint.Course","com.golfsprint.GolfSprint.CourseRequest")
                .properties(jpaProperties)
                .build();
    }
    @Bean(name="course-requestTransactionManager")
    public PlatformTransactionManager courseRequestPlatformTransactionManager(final @Qualifier("course-requestEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
