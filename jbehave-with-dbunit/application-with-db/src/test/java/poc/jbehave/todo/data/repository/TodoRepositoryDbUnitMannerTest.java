/**
 * 
 */
package poc.jbehave.todo.data.repository;

import static org.dbunit.Assertion.assertEquals;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import poc.jbehave.testing.config.EnableDbUnitFacilitation;
import poc.jbehave.testing.dbunit.assertion.DataChecker;
import poc.jbehave.testing.junit.rule.autoincrement.HsqldbAutoIncrementSettingRule;
import poc.jbehave.todo.data.bean.Todo;
import poc.jbehave.todo.data.config.EnableTodoApplicationData;

import com.google.common.collect.Lists;

/**
 * Test Cases for {@link TodoRepository}.
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class TodoRepositoryDbUnitMannerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoRepositoryDbUnitMannerTest.class);
    private static final String TODO_TABLE = "todo";
    private static final String ID_COLUMN = "id";

    @Autowired
    private TodoRepository todoRepository;
    @Rule
    @Autowired
    public HsqldbAutoIncrementSettingRule autoIncrementSettingRule;
    @Autowired
    private IDatabaseTester databaseTester;
    @Autowired
    private DataChecker dataChecker;

    @Configuration
    @EnableTodoApplicationData
    @EnableDbUnitFacilitation
    static class Config {

        @Autowired
        private DataSource dataSource;

        @Bean
        HsqldbAutoIncrementSettingRule autoIncrementSettingRule() {
            LOGGER.debug("hsqldbAutoIncrementSettingRule injected :-)");
            return new HsqldbAutoIncrementSettingRule() //
                    .withTable(TODO_TABLE) //
                    .withColumn(ID_COLUMN);
        }

        @Bean
        IDatabaseTester databaseTester() {
            return new DataSourceDatabaseTester(dataSource);
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Préparation de la base de données
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.NONE);

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("xml/todoDataSet.xml"));
        databaseTester.setDataSet(dataSet);

        databaseTester.onSetup();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        // Nettoyage de la base de données
        databaseTester.onTearDown();
    }

    private void verifyTableData(String tableName, String flatXmlDataSetPath) throws Exception {
        IDataSet actualDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(tableName);

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(flatXmlDataSetPath));
        ITable expectedTable = expectedDataSet.getTable(tableName);

        assertEquals(expectedTable, actualTable);
    }

    private void verifyDataSet(String flatXmlDataSetPath) throws Exception {
        IDataSet actualDataSet = databaseTester.getConnection().createDataSet();

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(flatXmlDataSetPath));

        assertEquals(expectedDataSet, actualDataSet);
    }

    private void verifyEmptyDataSet() throws Exception {
        IDataSet actualDataSet = databaseTester.getConnection().createDataSet();
        assertEquals(new DefaultDataSet(), actualDataSet);
    }

    private void verifyEmptyTable(String tableName) throws Exception {
        IDataSet actualDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(tableName);
        assertThat(actualTable.getRowCount()).isEqualTo(0);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(S)}.
     * 
     * @throws Exception
     */
    @Test
    public void testSaveS() throws Exception {
        // GIVEN
        Todo todo = new Todo(5L, "Refactoring.", false);

        // WHEN
        todoRepository.save(todo);

        // THEN
        verifyTableData(TODO_TABLE, "xml/saveSExpectedDataSet.xml");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public void testSaveIterableOfS() throws Exception {
        // WHEN
        todoRepository.save(Arrays.asList( //
                new Todo(5L, "Documenter le code.", true), //
                new Todo(6L, "Ajouter les package-info.", true)));

        // THEN
        verifyTableData(TODO_TABLE, "xml/saveIterableOfSExpectedDataSet.xml");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public void testFindOne() throws Exception {
        // WHEN
        Todo todo = todoRepository.findOne(2L);

        // THEN
        assertThat(todo).isNotNull();
        assertThat(todo.getId()).isEqualTo(2);
        assertThat(todo.getLabel()).isEqualTo("Constituer les jeux de données de test pour DbUnit.");

        verifyTableData(TODO_TABLE, "xml/todoDataSet.xml");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public void testExists() throws Exception {
        // WHEN
        boolean exists = todoRepository.exists(5L);

        // THEN
        assertThat(exists).isFalse();
        verifyTableData(TODO_TABLE, "xml/todoDataSet.xml");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#findAll()}.
     * 
     * @throws Exception
     */
    @Test
    public void testFindAll() throws Exception {
        // WHEN
        List<Todo> todos = Lists.newArrayList(todoRepository.findAll());

        // THEN
        assertThat(todos).hasSize(4);
        verifyTableData(TODO_TABLE, "xml/todoDataSet.xml");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#findAll(java.lang.Iterable)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public void testFindAllIterableOfID() throws Exception {
        // WHEN
        Iterable<Todo> todos = todoRepository.findAll(Arrays.asList(new Long(2), new Long(4)));

        // THEN
        assertThat(todos).hasSize(2);
        verifyTableData(TODO_TABLE, "xml/todoDataSet.xml");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#count()}.
     * 
     * @throws Exception
     */
    @Test
    public void testCount() throws Exception {
        // WHEN
        long count = todoRepository.count();

        // THEN
        assertThat(count).isEqualTo(4);
        verifyTableData(TODO_TABLE, "xml/todoDataSet.xml");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteID() throws Exception {
        // WHEN
        todoRepository.delete(1L);

        // THEN
        verifyTableData(TODO_TABLE, "xml/deleteIdExpectedDataSet.xml");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Object)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteT() throws Exception {
        // WHEN
        todoRepository.delete(new Todo(2L));

        // THEN
        verifyTableData(TODO_TABLE, "xml/deleteTExpectedDataSet.xml");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)}
     * .
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteIterableOfQextendsT() throws Exception {
        // WHEN
        todoRepository.delete(Arrays.asList(new Todo(2L), new Todo(4L)));

        // THEN
        verifyTableData(TODO_TABLE, "xml/deleteIterableOfQextendsTExpectedDataSet.xml");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#deleteAll()}.
     * 
     * @throws Exception
     */
    @Test
    public void testDeleteAll() throws Exception {
        // WHEN
        todoRepository.deleteAll();

        // THEN
        verifyEmptyTable(TODO_TABLE);
    }
}