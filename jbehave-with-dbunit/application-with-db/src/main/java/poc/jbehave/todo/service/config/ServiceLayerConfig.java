package poc.jbehave.todo.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration du contexte Spring de la couche de service.
 * 
 * @author Xavier Pigeon
 */
@Configuration
@ComponentScan(basePackages = { "poc.jbehave.todo.service" })
public class ServiceLayerConfig {}