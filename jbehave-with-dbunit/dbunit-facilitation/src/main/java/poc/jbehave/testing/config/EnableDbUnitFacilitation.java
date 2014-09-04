/**
 * 
 */
package poc.jbehave.testing.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Import;

/**
 * Annotation pour faciliter l'importation du contexte Spring de DbUnit
 * Facilitation.
 * 
 * @author Xavier Pigeon
 */
@Retention(RetentionPolicy.RUNTIME)
@Import(DbUnitFacilitationConfig.class)
public @interface EnableDbUnitFacilitation {}