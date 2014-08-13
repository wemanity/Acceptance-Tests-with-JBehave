package poc.jbehave.todo.data.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * Configuration du contexte Spring de la couche de persistance.
 * <ul>
 * <li>Transactions SQL : </br> Cette configuration fournit l'infrastructure JPA
 * composée d’une fabrique du gestionnaire d’entités JPA et du gestionnaire de
 * transactions associé.</li>
 * <li>Activation de Spring Data JPA et détection des beans de type Repository
 * :</br> L’annotation @{@link EnableJpaRepositories} active Spring Data JPA,
 * utilisé pour la mise en oeuvre de Hibernate / JPA : toutes les interfaces
 * contenues dans un package donné et étendant l’interface Repository de Spring
 * Data sont détectées puis interprétées. Par défaut, les requêtes JPA sont
 * générées à partir du nom des méthodes exposées dans ces interfaces. Les
 * implémentations personnalisées des Repository sont recherchées à l’aide du
 * suffixe Impl.</li>
 * </ul>
 * </p>
 * 
 * @author Xavier Pigeon
 */
@Configuration
@PropertySource("classpath:infrastructure.properties")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories("poc.jbehave.todo.data.repository")
@ComponentScan("poc.jbehave.todo.data.config.datasource")
public class DataAccessLayerConfig {

    private static final String BEAN_PACKAGE = "poc.jbehave.todo.data.bean";

    @Autowired
    private Environment environment;
    @Autowired
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan(BEAN_PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(this.buildHibernateProperties(environment));

        return factory;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory().getObject());
        transactionManager.setJpaDialect(new HibernateJpaDialect());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    private Properties buildHibernateProperties(Environment env) {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", //
                env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.default_schema", //
                env.getProperty("hibernate.default_schema"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", //
                env.getProperty("hibernate.hbm2ddl.auto"));

        hibernateProperties.setProperty("hibernate.show_sql", //
                env.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.format_sql", //
                env.getProperty("hibernate.format_sql"));
        hibernateProperties.setProperty("hibernate.use_sql_comments", //
                env.getProperty("hibernate.use_sql_comments"));
        hibernateProperties.setProperty("hibernate.generate_statistics",
                env.getProperty("hibernate.generate_statistics"));

        hibernateProperties.setProperty("hibernate.connection.autocommit", //
                env.getProperty("hibernate.connection.autocommit"));
        hibernateProperties.setProperty("hibernate.connection.pool_size", //
                env.getProperty("hibernate.connection.pool_size"));

        hibernateProperties.setProperty("javax.persistence.validation.mode", //
                env.getProperty("javax.persistence.validation.mode"));

        return hibernateProperties;
    }
}