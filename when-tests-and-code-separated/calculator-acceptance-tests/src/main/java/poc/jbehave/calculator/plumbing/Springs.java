/**
 * 
 */
package poc.jbehave.calculator.plumbing;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Initialisation du contexte de Spring pour l'injection de dépendances au moyen
 * d'annotations Spring.
 * 
 * @author Xavier Pigeon
 */
public class Springs {

    public static AnnotationConfigApplicationContext createContextFromClassPath(String resource) {
        return createContext(new ClassPathResource(resource));
    }

    public static AnnotationConfigApplicationContext createContext(Resource... resources) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(applicationContext);
        xmlReader.loadBeanDefinitions(resources);
        applicationContext.refresh();
        return applicationContext;
    }

    public static AnnotationConfigApplicationContext createAnnotatedContextFromBasePackages(String... basePackages) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan(basePackages);
        applicationContext.refresh();
        return applicationContext;
    }
}
