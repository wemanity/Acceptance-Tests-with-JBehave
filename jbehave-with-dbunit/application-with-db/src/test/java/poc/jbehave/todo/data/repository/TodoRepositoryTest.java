/**
 * 
 */
package poc.jbehave.todo.data.repository;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet("/xml/todoDataSet.xml")
public class TodoRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoRepositoryTest.class);

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private DataSource dataSource;

    @Rule
    public TestName testName = new TestName();

    @Configuration
    @Import(DataAccessLayerConfig.class)
    static class Config {}

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        LOGGER.debug("<<<<<<< SET UP: {} >>>>>>", testName.getMethodName());
        displayDbState(dataSource.getConnection());
        resetSqlAutoIncrementColumn();
        displayDbState(dataSource.getConnection());
    }

    private void resetSqlAutoIncrementColumn() {
        Connection jdbcConnection = null;

        try {
            jdbcConnection = dataSource.getConnection();
            // équivalent à : Connection jdbcConnection =
            // DriverManager.getConnection("jdbc:hsqldb:mem:poc-db", "sa", "");
            // Cf. les logs :
            // org.springframework.jdbc.datasource.SimpleDriverDataSource -
            // Creating
            // new JDBC Driver Connection to [jdbc:hsqldb:mem:poc-db]

            Statement restartStatement = jdbcConnection.createStatement();
            Long idMax = idMax(jdbcConnection);
            // Avec MySQL :
            // restartStatement.executeQuery("ALTER TABLE todo AUTO_INCREMENT=1;");
            // Avec HSQLDB :
            restartStatement.executeQuery("ALTER TABLE todo ALTER COLUMN id RESTART WITH " + (idMax + 1) + ";");
            restartStatement.close();

            jdbcConnection.close();
        } catch (SQLException e) {
            LOGGER.error("{}", e.getMessage());
            fail("Method resetIdentity failed!");
        }
    }

    private void displayDbState(Connection connection) throws SQLException {
        // La fonction IDENTITY() renvoie la valeur auto-incrément d'une colonne
        // relative à la session courante seulement, en partant de 0.
        LOGGER.debug("IDENTITY = {}", actualIdentity(connection));
        LOGGER.debug("MAX ID = {}", idMax(connection));
        LOGGER.debug("ID COUNT = {}", idCount(connection));
    }

    private Long idMax(Connection connection) throws SQLException {
        Statement maxIdStatement = connection.createStatement();
        ResultSet resultSet = maxIdStatement.executeQuery("SELECT max(id) FROM todo;");
        resultSet.next();
        Long idMax = resultSet.getLong(1);
        maxIdStatement.close();

        return idMax;
    }

    private Long idCount(Connection connection) throws SQLException {
        Statement idCountStatement = connection.createStatement();
        ResultSet resultSet = idCountStatement.executeQuery("SELECT count(DISTINCT id) FROM todo;");
        resultSet.next();
        Long idCount = resultSet.getLong(1);
        idCountStatement.close();

        return idCount;
    }

    private Long actualIdentity(Connection connection) throws SQLException {
        Statement identityStatement = connection.createStatement();
        ResultSet resultSet = identityStatement.executeQuery("CALL IDENTITY();");
        resultSet.next();
        Long identity = resultSet.getLong(1);
        identityStatement.close();

        return identity;
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        displayDbState(dataSource.getConnection());
        LOGGER.debug("<<<<<<<<<<<<<<< TEAR DOWN: {} >>>>>>>>>>>>>>", testName.getMethodName());
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(S)}.
     * 
     * @throws SQLException
     */
    @Test
    @ExpectedDataSet("/xml/saveTodoExpectedDataSet.xml")
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
    @ExpectedDataSet("/xml/saveIterableExpectedDataSet.xml")
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
    @ExpectedDataSet("/xml/todoDataSet.xml")
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
    @ExpectedDataSet("/xml/todoDataSet.xml")
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
    @ExpectedDataSet("/xml/todoDataSet.xml")
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
    @ExpectedDataSet("/xml/todoDataSet.xml")
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
    @ExpectedDataSet("/xml/todoDataSet.xml")
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
    @ExpectedDataSet("/xml/deleteIdExpectedDataSet.xml")
    public void testDeleteID() {
        todoRepository.delete(1L);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Object)}
     * .
     */
    @Test
    @ExpectedDataSet("/xml/deleteTExpectedDataSet.xml")
    public void testDeleteT() {
        todoRepository.delete(new Todo(2L));
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)}
     * .
     */
    @Test
    @ExpectedDataSet("/xml/deleteIterableExpectedDataSet.xml")
    public void testDeleteIterableOfQextendsT() {
        // WHEN
        todoRepository.delete(Arrays.asList(new Todo(2L), new Todo(4L)));
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#deleteAll()}.
     */
    @Test
    @ExpectedDataSet("/xml/deleteAllExpectedDataSet.xml")
    public void testDeleteAll() {
        todoRepository.deleteAll();
    }
}