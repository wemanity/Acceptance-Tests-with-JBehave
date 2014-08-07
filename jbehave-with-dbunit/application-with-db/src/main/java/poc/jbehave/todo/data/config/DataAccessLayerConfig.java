package poc.jbehave.todo.data.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration du contexte Spring de la couche de persistance.
 * 
 * @author Xavier Pigeon
 */
@Configuration
@ComponentScan(basePackages = { "poc.jbehave.todo.data" })
public class DataAccessLayerConfig {}