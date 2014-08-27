/**
 * 
 */
package poc.jbehave.testing.junit.rule.autoincrement;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

/**
 * Etat d'une table SQL.
 * 
 * @author Xavier Pigeon
 */
class SqlTableState {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlTableState.class);

    private Connection connection;
    private String tableName;
    private String columnName;
    private Long maxId;
    private Long count;
    private Long identity;

    /**
     * Constructeur.
     * 
     * @param connection
     * @param tableName
     * @param columnName
     */
    SqlTableState(Connection connection, String tableName, String columnName) {
        this.connection = connection;
        this.tableName = tableName;
        this.columnName = columnName;
    }

    void build() {
        try {
            refreshMaxIdAndCount();
            setIdentity(identity());

            LOGGER.debug("Table State: {}", toString());
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            fail("Method build failed!");
        }
    }

    private void refreshMaxIdAndCount() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( //
                "SELECT MAX(" + columnName + ")," //
                        + " COUNT(DISTINCT " + columnName + ")" //
                        + " FROM " + tableName + ";");
        resultSet.next();
        maxId = resultSet.getLong(1);
        count = resultSet.getLong(2);

        statement.close();
    }

    private Long identity() throws SQLException {
        Statement identityStatement = connection.createStatement();
        ResultSet resultSet = identityStatement.executeQuery("CALL IDENTITY();");

        Long identity = 0L;

        if (resultSet.next()) {
            identity = resultSet.getLong(1);
        }

        identityStatement.close();

        return identity;
    }

    /**
     * Getter for the field maxId.
     * 
     * @return the maxId
     */
    public Long getMaxId() {
        return maxId;
    }

    /**
     * Setter for the field maxId.
     * 
     * @param maxId the maxId to set
     */
    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    /**
     * Getter for the field count.
     * 
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * Setter for the field count.
     * 
     * @param count the count to set
     */
    public void setCount(Long count) {
        this.count = count;
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
     * Getter for the field identity.
     * 
     * @return the identity
     */
    public Long getIdentity() {
        return identity;
    }

    /**
     * Setter for the field identity.
     * 
     * @param identity the identity to set
     */
    public void setIdentity(Long identity) {
        this.identity = identity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(SqlTableState.class) //
                .add("tableName", tableName) //
                .add("columName", columnName) //
                .add("maxId", maxId) //
                .add("count", count) //
                .add("identity", identity) //
                .toString();
    }
}