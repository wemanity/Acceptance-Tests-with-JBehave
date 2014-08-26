/**
 * 
 */
package poc.jbehave.todo.test.junit.rule.autoincrement;

import static org.fest.assertions.Assertions.assertThat;

import java.sql.SQLException;
import java.util.Arrays;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import poc.jbehave.todo.data.bean.Todo;
import poc.jbehave.todo.data.config.DataAccessLayerConfig;
import poc.jbehave.todo.data.repository.TodoRepository;

import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.excilys.ebi.spring.dbunit.test.ExpectedDataSet;

/**
 * Test Case for {@link HsqldbAutoIncrementColumnSettingRule}.
 * 
 * @author Xavier Pigeon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet("/xml/todoDataSet.xml")
public class HsqldbAutoIncrementColumnSettingRuleTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HsqldbAutoIncrementColumnSettingRuleTest.class);

    @Rule
    @Autowired
    public HsqldbAutoIncrementColumnSettingRule hsqldbAutoIncrementColumnSettingRule;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TodoRepository todoRepository;

    @Rule
    public TestName testName = new TestName();

    @Configuration
    @Import(DataAccessLayerConfig.class)
    static class Config {

        @Bean
        HsqldbAutoIncrementColumnSettingRule hsqldbAutoIncrementColumnSettingRule() {
            return new HsqldbAutoIncrementColumnSettingRule() //
                    .withTable("todo") //
                    .withColumn("id");
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        LOGGER.info("<<< SET UP: {} >>>", testName.getMethodName());
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        LOGGER.info("<<< TEAR DOWN: {} >>>", testName.getMethodName());
    }

    /**
     * Test method for
     * {@link poc.jbehave.todo.test.junit.rule.autoincrement.HsqldbAutoIncrementColumnSettingRule#HsqldbAutoIncrementColumnSettingRule()}
     * .
     */
    @Test
    public void testHsqldbAutoIncrementColumnSettingRule() {
        assertThat(hsqldbAutoIncrementColumnSettingRule).isNotNull();
        assertThat(hsqldbAutoIncrementColumnSettingRule.getConnection()).isNotNull();
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(S)}.
     * 
     * @throws SQLException
     */
    @Test
    @ExpectedDataSet("/xml/saveSExpectedDataSet.xml")
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
    @ExpectedDataSet("/xml/saveIterableOfSExpectedDataSet.xml")
    public void testSaveIterableOfS() {
        todoRepository.save(Arrays.asList( //
                new Todo(5L, "Documenter le code.", true), //
                new Todo(6L, "Ajouter les package-info.", true)));
    }
}