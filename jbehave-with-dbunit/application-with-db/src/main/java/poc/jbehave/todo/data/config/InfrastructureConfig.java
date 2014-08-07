/**
 * 
 */
package poc.jbehave.todo.data.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * Configuration de la partie du contexte Spring de la couche de persistance
 * liée à l'infrastructure logicielle :
 * <ul>
 * <li>Base de données</li>
 * <li>Transactions SQL</li>
 * </ul>
 * </p>
 * 
 * @author Xavier Pigeon
 */
@Configuration
@EnableTransactionManagement
public class InfrastructureConfig {

    @Autowired
    private DataSource dataSource;
}