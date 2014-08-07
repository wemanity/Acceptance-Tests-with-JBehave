package poc.jbehave.todo.data.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Import;

/**
 * Annotation pour faciliter l'importation du contexte Spring de la couche de
 * persistance.
 * 
 * @author Xavier Pigeon
 */
@Retention(RetentionPolicy.RUNTIME)
@Import({ DataAccessLayerConfig.class, InfrastructureConfig.class })
public @interface EnableTodoApplicationData {}