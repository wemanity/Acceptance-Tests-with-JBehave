/**
 * 
 */
package poc.jbehave.todo.service.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * <p>
 * Test Case for {@link ServiceLayerConfig}.
 * </p>
 * <p>
 * L'objectif est de tester la configuration de Spring.
 * </p>
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceLayerConfig.class, loader = AnnotationConfigContextLoader.class)
public class ServiceLayerConfigTest {

    @Autowired
    private ServiceLayerConfig serviceLayerConfig;

    @Test
    public void should_configure_spring() {
        assertThat(serviceLayerConfig).isNotNull();
    }
}