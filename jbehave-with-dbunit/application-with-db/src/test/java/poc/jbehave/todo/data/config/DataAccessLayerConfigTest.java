/**
 * 
 */
package poc.jbehave.todo.data.config;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.fest.assertions.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * <p>
 * Test Case for {@link DataAccessLayerConfig}.
 * </p>
 * 
 * <p>
 * L'objectif est de tester la configuration de Spring.
 * </p>
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class DataAccessLayerConfigTest {

    @Autowired
    private DataAccessLayerConfig persistenceConfig;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private Environment environment;
    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;
    @Autowired
    private JpaTransactionManager transactionManager;
    @Autowired
    private PersistenceExceptionTranslationPostProcessor exceptionTranslation;
    @Autowired
    private HibernateExceptionTranslator hibernateExceptionTranslator;

    @Configuration
    @Import(DataAccessLayerConfig.class)
    static class Config {

        @Bean
        Environment environment() {
            Environment environment = createMock(Environment.class);
            expect(environment.getProperty("hsqldb.name")).andReturn("poc-db").once();
            expect(environment.getProperty("hsqldb.schema")).andReturn("classpath:/sql/schema.sql").once();
            expect(environment.getProperty("hsqldb.data")).andReturn("classpath:/sql/data.sql").once();
            expect(environment.getProperty("hibernate.dialect")).andReturn("org.hibernate.dialect.HSQLDialect").once();
            expect(environment.getProperty("hibernate.default_schema")).andReturn("PUBLIC").once();
            expect(environment.getProperty("hibernate.hbm2ddl.auto")).andReturn("validate").once();
            expect(environment.getProperty("hibernate.show_sql")).andReturn("true").once();
            expect(environment.getProperty("hibernate.format_sql")).andReturn("false").once();
            expect(environment.getProperty("hibernate.use_sql_comments")).andReturn("false").once();
            expect(environment.getProperty("hibernate.generate_statistics")).andReturn("false").once();
            expect(environment.getProperty("hibernate.connection.autocommit")).andReturn("true").once();
            expect(environment.getProperty("hibernate.connection.pool_size")).andReturn("2").once();
            expect(environment.getProperty("javax.persistence.validation.mode")).andReturn("none").once();

            replay(environment);

            return environment;
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {}

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {}

    @Test
    public void should_configure_spring() {
        assertThat(persistenceConfig).isNotNull();
        assertThat(dataSource).isNotNull();
        assertThat(entityManagerFactory).isNotNull();
        assertThat(transactionManager).isNotNull();
        assertThat(exceptionTranslation).isNotNull();
        assertThat(hibernateExceptionTranslator).isNotNull();

        verify(environment);
    }
}