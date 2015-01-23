/**
 * 
 */
package poc.jbehave.todo.data.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import poc.jbehave.todo.data.bean.Todo;
import poc.jbehave.todo.data.config.DataAccessLayerConfig;

import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.excilys.ebi.spring.dbunit.test.ExpectedDataSet;
import com.google.common.collect.Lists;

/**
 * <p>
 * Test Case for {@link TodoRepository}.
 * </p>
 * <h1>Utilisation de @TestExecutionListeners</h1>
 * <p>
 * L’annotation @TestExecutionListener fournie par spring-test permet
 * d’enregistrer des classes qui peuvent se brancher dans le cycle de vie
 * d’exécution d’un test (before test class, before test method, …).
 * </p>
 * <h2>Listener @DependencyInjectionTestExecutionListener</h2>
 * <p>
 * L’utilisation de ce listener défini par l’API spring-test permet simplement
 * d’utiliser les annotations d’injection de dépendance (@Autowired,@Resource,…)
 * sur des éléments de la classe de test.
 * </p>
 * <h2>Listener @DataSetTestExecutionListener</h2>
 * <p>
 * Ce listener est défini dans l’API spring-dbunit et permet l’utilisation de
 * l’annotation @Dataset sur une classe ou sur une méthode de test.</br> Cette
 * annotation utilise dbunit pour charger un jeu de données avant chaque test et
 * pour le supprimer après chaque test.</br> Par défaut, cette annotation
 * cherche un fichier nommé dataSet.xml dans le même package que la classe de
 * test qui porte l’annotation. Il est possible de changer le nom du fichier,
 * d’en charger plusieurs, bref de choisir son jeu de données.
 * </p>
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessLayerConfig.class, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(TodoRepositorySpringDbUnitTestMannerTest.TODO_DATA_SET)
public class TodoRepositorySpringDbUnitTestMannerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoRepositorySpringDbUnitTestMannerTest.class);

    static final String TODO_DATA_SET = "/xml/dataset/todoDataSet.xml";
    private static final String SAVE_S_EXPECTED_DATA_SET = "/xml/dataset/saveSExpectedDataSet.xml";
    private static final String SAVE_ITERABLE_OF_S_EXPECTED_DATA_SET = "/xml/dataset/saveIterableOfSExpectedDataSet.xml";
    private static final String DELETE_ID_EXPECTED_DATA_SET = "/xml/dataset/deleteIdExpectedDataSet.xml";
    private static final String DELETE_T_EXPECTED_DATA_SET = "/xml/dataset/deleteTExpectedDataSet.xml";
    private static final String DELETE_ITERABLE_OF_Q_EXTENDS_T_EXPECTED_DATA_SET = "/xml/dataset/deleteIterableOfQextendsTExpectedDataSet.xml";
    private static final String DELETE_ALL_EXPECTED_DATA_SET = "/xml/dataset/deleteAllExpectedDataSet.xml";

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private DataSource dataSource;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        Connection jdbcConnection = dataSource.getConnection();
        resetSqlAutoIncrementColumn(jdbcConnection);
        jdbcConnection.close();
    }

    /**
     * <p>
     * Repositionner la valeur courante de l'auto-incrément d'une colonne SQL.
     * </p>
     * <p>
     * Ce repositionnement doit être fait avant chaque test.
     * </p>
     * 
     * @param connection
     *            la connexion JDBC
     */
    private void resetSqlAutoIncrementColumn(Connection connection) {
        try {
            Statement restartStatement = connection.createStatement();
            Long idMax = idMax(connection);
            restartStatement.executeQuery("ALTER TABLE todo ALTER COLUMN id RESTART WITH " + (idMax + 1) + ";");
            restartStatement.close();
        } catch (SQLException e) {
            LOGGER.error("{}", e.getMessage());
            fail("Method resetSqlAutoIncrementColumn failed!");
        }
    }

    /**
     * Renvoyer la valeur de l'identifiant maximal d'une table.
     * 
     * @param connection
     *            la connexion JDBC
     * @return la valeur de l'identifiant maximal d'une table
     * @throws SQLException
     *             en cas d'erreur SQL
     */
    private Long idMax(Connection connection) throws SQLException {
        Statement maxIdStatement = connection.createStatement();
        ResultSet resultSet = maxIdStatement.executeQuery("SELECT MAX(id) FROM todo;");
        resultSet.next();
        Long idMax = resultSet.getLong(1);
        maxIdStatement.close();

        return idMax;
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(S)}.
     * 
     * @throws SQLException
     */
    @Test
    @ExpectedDataSet(SAVE_S_EXPECTED_DATA_SET)
    public void testSaveS() throws SQLException {
        // GIVEN
        Todo todo = new Todo(5L, "Refactoring.", false);

        // WHEN
        todoRepository.save(todo);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)}
     * .
     */
    @Test
    @ExpectedDataSet(SAVE_ITERABLE_OF_S_EXPECTED_DATA_SET)
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
    @ExpectedDataSet(TODO_DATA_SET)
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
    @ExpectedDataSet(TODO_DATA_SET)
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
    @ExpectedDataSet(TODO_DATA_SET)
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
    @ExpectedDataSet(TODO_DATA_SET)
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
    @ExpectedDataSet(TODO_DATA_SET)
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
    @ExpectedDataSet(DELETE_ID_EXPECTED_DATA_SET)
    public void testDeleteID() {
        todoRepository.delete(1L);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Object)}
     * .
     */
    @Test
    @ExpectedDataSet(DELETE_T_EXPECTED_DATA_SET)
    public void testDeleteT() {
        todoRepository.delete(new Todo(2L));
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)}
     * .
     */
    @Test
    @ExpectedDataSet(DELETE_ITERABLE_OF_Q_EXTENDS_T_EXPECTED_DATA_SET)
    public void testDeleteIterableOfQextendsT() {
        // WHEN
        todoRepository.delete(Arrays.asList(new Todo(2L), new Todo(4L)));
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#deleteAll()}.
     */
    @Test
    @ExpectedDataSet(DELETE_ALL_EXPECTED_DATA_SET)
    public void testDeleteAll() {
        todoRepository.deleteAll();
    }
}