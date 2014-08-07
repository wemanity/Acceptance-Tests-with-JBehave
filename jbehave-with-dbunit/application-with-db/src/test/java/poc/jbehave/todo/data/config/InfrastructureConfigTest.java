/**
 * 
 */
package poc.jbehave.todo.data.config;

import static org.fest.assertions.Assertions.assertThat;

import javax.sql.DataSource;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Test Case for {@link InfrastructureConfig}.
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class InfrastructureConfigTest {

    @Autowired
    private InfrastructureConfig infrastructureConfig;
    @Autowired
    private DataSource dataSource;

    @Configuration
    @ComponentScan(basePackages = "poc.jbehave.todo.data")
    static class Config {

        @Bean
        public DataSource dataSource() {
            return EasyMock.createMock(DataSource.class);
        }
    }

    /**
     * Set up the test.
     */
    @Before
    public void setUp() {

    }

    @Test
    public void should_configure_spring() {
        // GIVEN
        EasyMock.replay(dataSource);

        // WHEN
        // IoC by Spring Framework

        // THEN
        assertThat(infrastructureConfig).isNotNull();
        EasyMock.verify(dataSource);
    }
}