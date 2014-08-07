package poc.jbehave.todo.spring;

import org.springframework.context.annotation.Configuration;

import poc.jbehave.todo.service.config.EnableTodoApplicationService;

/**
 * Configuration du contexte Spring de l'application Todo.
 * 
 * @author Xavier Pigeon
 */
@Configuration
@EnableTodoApplicationService
public class TodoApplicationSpringConfig {}