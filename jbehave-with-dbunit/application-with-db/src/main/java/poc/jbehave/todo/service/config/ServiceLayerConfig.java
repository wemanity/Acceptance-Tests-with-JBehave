package poc.jbehave.todo.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "poc.jbehave.todo.service" })
public class ServiceLayerConfig {}