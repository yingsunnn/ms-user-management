package com.ying.msusermanagement.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "userManagementManagerFactory",
    transactionManagerRef = "userManagementTransactionManager",
    basePackages = {"com.ying.msusermanagement.repository"})
public class UserManagementDBConfig {

  @Primary
  @Bean(name = "userManagementDSProperties")
  @ConfigurationProperties(prefix = "spring.datasource.user-management")
  public DataSourceProperties userManagementDSProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean(name = "userManagementDataSource")
  public DataSource userManagementDataSource(
      @Qualifier("userManagementDSProperties") final DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }

  @Primary
  @Bean(name = "userManagementManagerFactory")
  public LocalContainerEntityManagerFactoryBean userManagementManagerFactory(
      EntityManagerFactoryBuilder entityManagerFactoryBuilder,
      @Qualifier("userManagementDataSource") DataSource dataSource
  ) {
    return entityManagerFactoryBuilder
        .dataSource(dataSource)
        .packages("com.ying.msusermanagement.entity")
        .persistenceUnit("userManagementPersistenceUnit")
        .build();
  }

  @Primary
  @Bean(name = "userManagementTransactionManager")
  public PlatformTransactionManager userManagementTransactionManager(
      @Qualifier("userManagementManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
