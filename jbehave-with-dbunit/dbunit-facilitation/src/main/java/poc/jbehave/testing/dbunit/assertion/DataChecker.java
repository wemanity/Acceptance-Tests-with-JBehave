/**
 * 
 */
package poc.jbehave.testing.dbunit.assertion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.Assertion.assertEquals;

import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Vérification des données.
 * </p>
 * <p>
 * Cette classe nécessite l'injection du {@link IDatabaseTester} de DbUnit, issu
 * du test JUnit.
 * </p>
 * 
 * @author Xavier Pigeon
 */
@Component
public class DataChecker {

    @Autowired
    private IDatabaseTester databaseTester;

    public void verifyTableData(String tableName, String flatXmlDataSetPath) throws Exception {
        IDataSet actualDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(tableName);

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(flatXmlDataSetPath));
        ITable expectedTable = expectedDataSet.getTable(tableName);

        assertEquals(expectedTable, actualTable);
    }

    public void verifyDataSet(String flatXmlDataSetPath) throws Exception {
        IDataSet actualDataSet = databaseTester.getConnection().createDataSet();

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(flatXmlDataSetPath));

        assertEquals(expectedDataSet, actualDataSet);
    }

    public void verifyEmptyDataSet() throws Exception {
        IDataSet actualDataSet = databaseTester.getConnection().createDataSet();
        assertEquals(new DefaultDataSet(), actualDataSet);
    }

    public void verifyEmptyTable(String tableName) throws Exception {
        IDataSet actualDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(tableName);
        assertThat(actualTable.getRowCount()).isEqualTo(0);
    }
}