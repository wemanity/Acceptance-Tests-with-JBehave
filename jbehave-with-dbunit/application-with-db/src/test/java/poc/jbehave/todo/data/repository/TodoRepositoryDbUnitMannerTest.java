/**
 * 
 */
package poc.jbehave.todo.data.repository;

import static org.dbunit.Assertion.assertEquals;
import static org.fest.assertions.Assertions.assertThat;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import poc.jbehave.testing.junit.rule.autoincrement.HsqldbAutoIncrementSettingRule;
import poc.jbehave.todo.data.bean.Todo;
import poc.jbehave.todo.data.config.DataAccessLayerConfig;

import com.google.common.collect.Lists;

/**
 * Test Case for {@link TodoRepository}.
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class TodoRepositoryDbUnitMannerTest {

    private static final String TODO_TABLE = "todo";
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private DataSource dataSource;
    @Rule
    @Autowired
    public HsqldbAutoIncrementSettingRule hsqldbAutoIncrementSettingRule;
    private IDatabaseTester databaseTester;

    @Configuration
    @Import(DataAccessLayerConfig.class)
    static class Config {

        @Bean
        HsqldbAutoIncrementSettingRule hsqldbAutoIncrementSettingRule() {
            return new HsqldbAutoIncrementSettingRule() //
                    .withTable(TODO_TABLE) //
                    .withColumn("id");
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Préparation de la base de données
        databaseTester = new DataSourceDatabaseTester(dataSource);
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

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(S)}.
     * 
     * @throws Exception
     * @throws SQLException
     */
    @Test
    public void testSaveS() throws SQLException, Exception {
        // GIVEN
        Todo todo = new Todo(5L, "Refactoring.", false);

        // WHEN
        todoRepository.save(todo);

        // THEN
        IDataSet actualDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(TODO_TABLE);

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("xml/saveSExpectedDataSet.xml"));
        ITable expectedTable = expectedDataSet.getTable(TODO_TABLE);

        assertEquals(expectedTable, actualTable);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)}
     * .
     */
    @Test
    public void testSaveIterableOfS() {
        todoRepository.save(Arrays.asList( //
                new Todo(5L, "Documenter le code.", true), //
                new Todo(6L, "Ajouter les package-info.", true)));
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)}
     * .
     */
    @Test
    public void testFindOne() {
        // WHEN
        Todo todo = todoRepository.findOne(2L);

        // THEN
        assertThat(todo).isNotNull();
        assertThat(todo.getId()).isEqualTo(2);
        assertThat(todo.getLabel()).isEqualTo("Constituer les jeux de données de test pour DbUnit.");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}
     * .
     */
    @Test
    public void testExists() {
        // WHEN
        boolean exists = todoRepository.exists(5L);

        // THEN
        assertThat(exists).isFalse();
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#findAll()}.
     */
    @Test
    public void testFindAll() {
        // WHEN
        List<Todo> todos = Lists.newArrayList(todoRepository.findAll());

        // THEN
        assertThat(todos).hasSize(4);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#findAll(java.lang.Iterable)}
     * .
     */
    @Test
    public void testFindAllIterableOfID() {
        // WHEN
        Iterable<Todo> todos = todoRepository.findAll(Arrays.asList(new Long(2), new Long(4)));

        // THEN
        assertThat(todos).hasSize(2);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#count()}.
     */
    @Test
    public void testCount() {
        // WHEN
        long count = todoRepository.count();

        // THEN
        assertThat(count).isEqualTo(4);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)}
     * .
     */
    @Test
    public void testDeleteID() {
        todoRepository.delete(1L);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Object)}
     * .
     */
    @Test
    public void testDeleteT() {
        todoRepository.delete(new Todo(2L));
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)}
     * .
     */
    @Test
    public void testDeleteIterableOfQextendsT() {
        // WHEN
        todoRepository.delete(Arrays.asList(new Todo(2L), new Todo(4L)));
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#deleteAll()}.
     */
    @Test
    public void testDeleteAll() {
        todoRepository.deleteAll();
    }
}