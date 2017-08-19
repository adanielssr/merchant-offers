package com.github.adanielssr.merchant.offers.domain;

import java.text.MessageFormat;
import java.util.Properties;
import javax.sql.DataSource;

import com.github.adanielssr.merchant.offers.domain.entities.Offer;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@PropertySource("classpath:merchant-offers-domain.properties")
@EnableJpaRepositories(basePackages = "com.github.adanielssr.merchant.offers.domain.repositories")
public class DomainConfiguration {

    private static final String PROPERTY_NAME_AUTH_DOMAIN_JDBC_DRIVER_CLASS_NAME = "com.github.adanielssr.merchant.offers.domain.jdbc.driverClassName";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_JDBC_URL = "com.github.adanielssr.merchant.offers.domain.jdbc.baseurl";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_JDBC_SERVER_HOST = "com.github.adanielssr.merchant.offers.db.serverName";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_JDBC_SERVER_PORT = "com.github.adanielssr.merchant.offers.db.serverPort";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_JDBC_DATABASE = "com.github.adanielssr.merchant.offers.db.database";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_JDBC_USERNAME = "com.github.adanielssr.merchant.offers.db.user";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_JDBC_PASSWORD = "com.github.adanielssr.merchant.offers.db.password";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_DBCP_MAX_ACTIVE = "com.github.adanielssr.merchant.offers.domain.dbcp.maxActive";

    private static final String PROPERTY_NAME_DBCP_MAX_IDLE = "com.github.adanielssr.merchant.offers.domain.dbcp.maxIdle";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_DBCP_MAX_WAIT = "com.github.adanielssr.merchant.offers.domain.dbcp.maxWait";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_HIBERNATE_DIALECT = "com.github.adanielssr.merchant.offers.domain.hibernate.dialect";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_HIBERNATE_SHOW_SQL = "com.github.adanielssr.merchant.offers.domain.hibernate.show_sql";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_PERSISTENCE_VALIDATION_MODE = "com.github.adanielssr.merchant.offers.domain.persistence.validation.mode";

    private static final String PROPERTY_NAME_AUTH_DOMAIN_HIBERNATE_HBM2DDL_AUTO = "com.github.adanielssr.merchant.offers.domain.hibernate.hbm2ddl.auto";

    private static final String PROPERTY_KEY_JAVAX_PERSISTENCE_VALIDATION_MODE = "javax.persistence.validation.mode";

    private static final String PROPERTY_KEY_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_JDBC_DRIVER_CLASS_NAME));

        String jdbcBase = env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_JDBC_URL);
        String server = env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_JDBC_SERVER_HOST);
        String serverPort = env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_JDBC_SERVER_PORT);
        String serverDatabase = env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_JDBC_DATABASE);

        dataSource.setUrl(MessageFormat.format(jdbcBase, server, serverPort, serverDatabase));
        dataSource.setUsername(env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_JDBC_USERNAME));
        dataSource.setPassword(env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_JDBC_PASSWORD));
        dataSource.setMaxActive(env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_DBCP_MAX_ACTIVE, Integer.class));
        dataSource.setMaxIdle(env.getProperty(PROPERTY_NAME_DBCP_MAX_IDLE, Integer.class));
        dataSource.setMaxWait(env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_DBCP_MAX_WAIT, Integer.class));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactory.setJpaProperties(jpaProperties());
        //specific properties set
        entityManagerFactory.setPersistenceUnitName("offersdb");
        entityManagerFactory.setPackagesToScan(Offer.class.getPackage().getName());
        return entityManagerFactory;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabasePlatform(env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_HIBERNATE_DIALECT));
        jpaVendorAdapter.setShowSql(env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_HIBERNATE_SHOW_SQL, Boolean.class));
        return jpaVendorAdapter;
    }

    @Bean
    public Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty(PROPERTY_KEY_JAVAX_PERSISTENCE_VALIDATION_MODE,
                env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_PERSISTENCE_VALIDATION_MODE));
        jpaProperties.setProperty(PROPERTY_KEY_HIBERNATE_HBM2DDL_AUTO,
                env.getProperty(PROPERTY_NAME_AUTH_DOMAIN_HIBERNATE_HBM2DDL_AUTO));
        return jpaProperties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
