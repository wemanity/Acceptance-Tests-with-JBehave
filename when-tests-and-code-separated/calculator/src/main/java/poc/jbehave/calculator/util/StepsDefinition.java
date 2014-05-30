/**
 * 
 */
package poc.jbehave.calculator.util;

import java.lang.annotation.Documented;

import org.springframework.stereotype.Component;

/**
 * <p>
 * Annotation dont le but est de :
 * <ul>
 * <li>marquer une classe comme contenant des définitions d'étapes JBehave,</li>
 * <li>permettre à Spring de détecter une telle classe lors de la construction
 * de son contexte.</li>
 * </ul>
 * 
 * @author Xavier Pigeon
 */
@Documented
@Component
public @interface StepsDefinition {}
