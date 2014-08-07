/**
 * 
 */
package poc.jbehave.todo.data.config;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * <p>
 * Test Case for {@link DataAccessLayerConfig}
 * </p>
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
    private DataAccessLayerConfig dataAccessLayerConfig;

    @Configuration
    @ComponentScan(basePackages = "poc.jbehave.todo.data")
    static class Config {}

    @Test
    public void should_configure_spring() {
        assertThat(dataAccessLayerConfig).isNotNull();
    }
}