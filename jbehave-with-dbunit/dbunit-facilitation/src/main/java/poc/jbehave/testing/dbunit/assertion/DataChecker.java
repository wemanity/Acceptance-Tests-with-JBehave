/**
 * 
 */
package poc.jbehave.testing.dbunit.assertion;

import org.dbunit.IDatabaseTester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Vérification des données.
 * 
 * @author Xavier Pigeon
 */
@Component
public class DataChecker {

    @Autowired
    private IDatabaseTester databaseTester;
}