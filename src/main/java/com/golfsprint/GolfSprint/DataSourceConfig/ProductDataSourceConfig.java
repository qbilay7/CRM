/*package com.golfsprint.GolfSprint.DataSourceConfig;

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
@EnableJpaRepositories(basePackages = "com.golfsprint.GolfSprint.Shop",
        entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "productTransactionManager")
public class ProductDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.product")
    public DataSourceProperties productDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean(name="Product")
    @ConfigurationProperties("spring.datasource.product.configuration")
    public DataSource productDataSource(){
        return productDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
    @Bean(name = "productEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean productLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier("Product") DataSource dataSource){
        Map<String, Object> jpaProperties = new HashMap<>();

        // Set your Hibernate and JPA properties here
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop"); //update
        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.golfsprint.GolfSprint.Shop","com.golfsprint.GolfSprint.Company","com.golfsprint.GolfSprint.Delivery","com.golfsprint.GolfSprint.User")
                .properties(jpaProperties)
                .build();
    }
    @Bean(name="productTransactionManager")
    public PlatformTransactionManager productPlatformTransactionManager(final @Qualifier("productEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
*/