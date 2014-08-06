/**
 * 
 */
package poc.jbehave.data.config;

import static org.fest.assertions.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Test Case for {@link HsqldbDataConfig}.
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class HsqldbDataConfigTest {

    @Configuration
    @ComponentScan(basePackages = { "poc.jbehave.data.config" })
    static class Config {}

    @Autowired
    private HsqldbDataConfig hsqldbDataConfig;
    @Autowired
    private DataSource dataSource;

    @After
    public void tearDown() throws Exception {
        ((EmbeddedDatabase) dataSource).shutdown();
    }

    /**
     * Test method for
     * {@link poc.jbehave.data.config.HsqldbDataConfig#dataSource(java.util.Properties)}
     * .
     * 
     * @throws ClassNotFoundException
     */
    @Test
    public void should_prepare_datasource() throws ClassNotFoundException {
        // GIVEN
        Class<?> expectedClazz = Class
                .forName("org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory$EmbeddedDataSourceProxy");

        // WHEN
        // IoC by Spring Framework

        // THEN
        assertThat(dataSource).isNotNull();
        assertThat(dataSource).isInstanceOf(expectedClazz);
    }
}