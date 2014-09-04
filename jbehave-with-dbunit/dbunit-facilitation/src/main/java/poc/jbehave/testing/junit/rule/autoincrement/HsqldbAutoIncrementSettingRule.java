/**
 * 
 */
package poc.jbehave.testing.junit.rule.autoincrement;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Règle pour réinitialisiser la valeur d'un auto-incrément avec HSQLDB.
 * 
 * @author Xavier Pigeon
 */
@Component
public class HsqldbAutoIncrementSettingRule extends ExternalResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(HsqldbAutoIncrementSettingRule.class);

    // Dépendances
    @Autowired
    private DataSource dataSource;
    private Connection connection;

    // Représentation interne
    private String tableName;
    private String columnName;
    private Long incrementReference;
    private boolean incrementReferenceValued = false;

    /**
     * Constructeur par défaut.
     */
    public HsqldbAutoIncrementSettingRule() {
        super();
        LOGGER.debug("Instance ready :-)");
    }

    /**
     * La {@link DataSource} ayant été injectée par Spring, cette classe
     * utilisera une seule connexion dans ses méthodes.
     */
    @PostConstruct
    void initialize() {
        assertThat(dataSource).isNotNull();

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Impossible to get connection!");
            fail("Impossible to get connection!");
        }

        assertThat(connection).isNotNull();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws SQLException
     */
    @Override
    protected void before() throws SQLException {
        LOGGER.info("<<< BEFORE >>>");
        resetSqlAutoIncrementColumn(connection);
    }

    private void resetSqlAutoIncrementColumn(Connection connection) {
        try {
            Statement restartStatement = connection.createStatement();
            String sqlQuery = "ALTER TABLE " + tableName + " ALTER COLUMN " + columnName //
                    + " RESTART WITH " + incrementReference() + ";";
            LOGGER.debug("SQL query: {}", sqlQuery);
            restartStatement.executeQuery(sqlQuery);
            restartStatement.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            fail("Method resetSqlAutoIncrementColumn failed!");
        }
    }

    /**
     * Fournir la valeur de référence pour la colonne auto-incrément de la
     * table.
     * 
     * @return la valeur de référence
     */
    private Long incrementReference() {
        if (!incrementReferenceValued) {
            SqlTableState sqlTableState = tableState(connection, tableName, columnName);
            LOGGER.debug("Table State: {}", sqlTableState.toString());
            incrementReference = sqlTableState.getMaxId() + 1;
            incrementReferenceValued = true;
        }

        return incrementReference;
    }

    private SqlTableState tableState(Connection connection, String tableName, String columnName) {
        SqlTableState sqlTableState = new SqlTableState(connection, tableName, columnName);
        sqlTableState.build();
        LOGGER.debug("Table State: {}", sqlTableState.toString());

        return sqlTableState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void after() {
        SqlTableState sqlTableState = tableState(connection, tableName, columnName);
        LOGGER.debug("Table State: {}", sqlTableState.toString());

        LOGGER.info("<<< AFTER >>>");
    }

    public HsqldbAutoIncrementSettingRule withTable(String tableName) {
        setTableName(tableName);
        return this;
    }

    public HsqldbAutoIncrementSettingRule withColumn(String columnName) {
        setColumnName(columnName);
        return this;
    }

    /**
     * Getter for the field tableName.
     * 
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Setter for the field tableName.
     * 
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Getter for the field columnName.
     * 
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Setter for the field columnName.
     * 
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Getter for the field dataSource.
     * 
     * @return the dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Setter for the field dataSource.
     * 
     * @param dataSource the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Getter for the field connection.
     * 
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Setter for the field connection.
     * 
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Getter for the field incrementReference.
     * 
     * @return the incrementReference
     */
    public Long getIncrementReference() {
        return incrementReference;
    }

    /**
     * Setter for the field incrementReference.
     * 
     * @param incrementReference the incrementReference to set
     */
    public void setIncrementReference(Long incrementReference) {
        this.incrementReference = incrementReference;
    }

    /**
     * Getter for the field incrementReferenceValued.
     * 
     * @return the incrementReferenceValued
     */
    public boolean isIncrementReferenceValued() {
        return incrementReferenceValued;
    }

    /**
     * Setter for the field incrementReferenceValued.
     * 
     * @param incrementReferenceValued the incrementReferenceValued to set
     */
    public void setIncrementReferenceValued(boolean incrementReferenceValued) {
        this.incrementReferenceValued = incrementReferenceValued;
    }
}