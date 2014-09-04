/**
 * 
 */
package poc.jbehave.todo.data.config.datasource;

import static org.fest.assertions.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Test Case for {@link HsqldbDataSourceConfig}.
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HsqldbDataSourceConfig.class, loader = AnnotationConfigContextLoader.class)
public class HsqldbDataSourceConfigTest {

    @Autowired
    private HsqldbDataSourceConfig hsqldbDataSourceConfig;
    @Autowired
    private DataSource dataSource;

    @After
    public void tearDown() throws Exception {
        ((EmbeddedDatabase) dataSource).shutdown();
    }

    /**
     * Test method for
     * {@link poc.jbehave.todo.data.config.datasource.HsqldbDataSourceConfig#dataSource(java.util.Properties)}
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
        assertThat(hsqldbDataSourceConfig).isNotNull();
        assertThat(dataSource).isNotNull();
        assertThat(dataSource).isInstanceOf(expectedClazz);
    }
}