package poc.jbehave.todo.data.config.datasource;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Construction d'une base de données embarquée avec Spring Framework.
 * 
 * @author Xavier Pigeon
 */
@Configuration
@PropertySource("classpath:datasource.properties")
public class HsqldbDataSourceConfig {

    private static final String HSQLDB_NAME = "hsqldb.name";
    private static final String HSQLDB_DATA = "hsqldb.data";
    private static final String HSQLDB_SCHEMA = "hsqldb.schema";

    @Autowired
    private Environment env;

    /**
     * Déclarer une {@link DataSource} dans le contexte de Spring, en
     * construisant une base de données embarquée.
     * 
     * @param hibernateProperties les propriétés pour la création de la base de
     *            données
     * @return un objet {@link EmbeddedDatabase}
     */
    @Bean
    public DataSource dataSource(Properties hibernateProperties) {
        return new EmbeddedDatabaseBuilder() //
                .setName(env.getProperty(HSQLDB_NAME)) //
                .setType(EmbeddedDatabaseType.HSQL) //
                .addScript(env.getProperty(HSQLDB_SCHEMA)) //
                // .addScript(env.getProperty(HSQLDB_DATA)) //
                .build();
    }
}