/**
 * 
 */
package poc.jbehave.todo.data.repository;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    private static final String TODO_TABLE = "todo";
    private static final String ID_COLUMN = "id";
    private static final String TODO_DATA_SET = "xml/dataset/todoDataSet.xml";
    private static final String SAVE_S_EXPECTED_DATA_SET = "xml/dataset/saveSExpectedDataSet.xml";
    private static final String SAVE_ITERABLE_OF_S_EXPECTED_DATA_SET = "xml/dataset/saveIterableOfSExpectedDataSet.xml";
    private static final String DELETE_ID_EXPECTED_DATA_SET = "xml/dataset/deleteIdExpectedDataSet.xml";
    private static final String DELETE_T_EXPECTED_DATA_SET = "xml/dataset/deleteTExpectedDataSet.xml";
    private static final String DELETE_ITERABLE_OF_Q_EXTENDS_T_EXPECTED_DATA_SET = "xml/dataset/deleteIterableOfQextendsTExpectedDataSet.xml";

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
                .getResourceAsStream(TODO_DATA_SET));
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
        dataChecker.verifyTableData(TODO_TABLE, SAVE_S_EXPECTED_DATA_SET);
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
        dataChecker.verifyTableData(TODO_TABLE, SAVE_ITERABLE_OF_S_EXPECTED_DATA_SET);
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

        dataChecker.verifyTableData(TODO_TABLE, TODO_DATA_SET);
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
        dataChecker.verifyTableData(TODO_TABLE, TODO_DATA_SET);
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
        dataChecker.verifyTableData(TODO_TABLE, TODO_DATA_SET);
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
        dataChecker.verifyTableData(TODO_TABLE, TODO_DATA_SET);
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
        dataChecker.verifyTableData(TODO_TABLE, TODO_DATA_SET);
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
        dataChecker.verifyTableData(TODO_TABLE, DELETE_ID_EXPECTED_DATA_SET);
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
        dataChecker.verifyTableData(TODO_TABLE, DELETE_T_EXPECTED_DATA_SET);
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
        dataChecker.verifyTableData(TODO_TABLE, DELETE_ITERABLE_OF_Q_EXTENDS_T_EXPECTED_DATA_SET);
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
        dataChecker.verifyEmptyTable(TODO_TABLE);
    }
}