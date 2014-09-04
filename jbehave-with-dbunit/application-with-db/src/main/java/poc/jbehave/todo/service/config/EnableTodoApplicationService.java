package poc.jbehave.todo.service.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Import;

/**
 * Annotation pour faciliter l'importation du contexte Spring de la couche de
 * service.
 * 
 * @author Xavier Pigeon
 */
@Retention(RetentionPolicy.RUNTIME)
@Import(ServiceLayerConfig.class)
public @interface EnableTodoApplicationService {}