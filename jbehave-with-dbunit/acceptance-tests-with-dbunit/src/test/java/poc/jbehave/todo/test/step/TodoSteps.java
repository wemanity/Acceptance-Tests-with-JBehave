/**
 * 
 */
package poc.jbehave.todo.test.step;

import static org.fest.assertions.Assertions.assertThat;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import poc.jbehave.plumbing.StepsDefinition;
import poc.jbehave.testing.config.EnableDbUnitFacilitation;
import poc.jbehave.testing.dbunit.assertion.DataChecker;
import poc.jbehave.todo.service.api.AllTodosDto;
import poc.jbehave.todo.service.api.ITodoService;
import poc.jbehave.todo.service.api.NewTodoDto;
import poc.jbehave.todo.service.domain.Todo;

/**
 * Définition des étapes JBehave.
 * 
 * @author Xavier Pigeon
 */
@StepsDefinition
public class TodoSteps {

    private static final String ALL_TODOS_DATA_SET = "dataset/todoDataSet.xml";
    private static final String NEW_TODO_EXPECTED_DATA_SET = "dataset/newTodoExpectedDataSet.xml";
    private static final String DELETED_TODO_EXPECTED_DATA_SET = "dataset/deletedTodoExpectedDataSet.xml";

    @Autowired
    private ITodoService todoService;
    private AllTodosDto allTodosDto;
    private NewTodoDto newTodoDto;
    @Autowired
    private IDatabaseTester databaseTester;
    @Autowired
    private DataChecker dataChecker;

    @Configuration
    @EnableDbUnitFacilitation
    static class Config {

        @Autowired
        private DataSource dataSource;

        @Bean
        IDatabaseTester databaseTester() {
            return new DataSourceDatabaseTester(dataSource);
        }
    }

    @Given("some todos available")
    public void someTodosAvailable() throws Exception {
        // Préparation de la base de données
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.NONE);

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(ALL_TODOS_DATA_SET));
        databaseTester.setDataSet(dataSet);

        databaseTester.onSetup();
    }

    @When("I list all my todos")
    public void iListAllMyTodos() {
        allTodosDto = todoService.getAllTodos();
    }

    @When("I add a new todo \"$label\"")
    public void iAddNewTodo(String label) {
        newTodoDto = todoService.addNewTodo(label);
    }

    @When("I delete the todo number $number")
    public void iDeleteTodoNumber(Long number) {
        todoService.deleteOldTodo(number);
    }

    @Then("I get $number todos")
    public void iGetTodos(Integer number) throws Exception {
        assertThat(allTodosDto.getTodos()).hasSize(number);
        assertThat(allTodosDto.getTodos()).containsExactly( //
                new Todo(1L, "Ecrire les tests d'acceptation JBehave.", false), //
                new Todo(2L, "Constituer les jeux de données de test pour DbUnit.", false), //
                new Todo(3L, "Implémenter la couche de service.", false), //
                new Todo(4L, "Implémenter la couche de persistance.", false));

        dataChecker.verifyTableData("todo", ALL_TODOS_DATA_SET);
    }

    @Then("the last added todo is labeled \"$label\"")
    public void theLastAddedTodoIsLabeled(String label) {
        assertThat(newTodoDto.getNewTodo().getLabel()).isEqualTo(label);
    }

    @Then("the last added todo is not done")
    public void theLastAddedTodoIsNotDone() {
        assertThat(newTodoDto.getNewTodo().getDone()).isFalse();
    }

    @Then("the list of todos has grew by this last todo")
    public void theListOfTodosHasGrewByThisLastTodo() throws Exception {
        dataChecker.verifyTableData("todo", NEW_TODO_EXPECTED_DATA_SET);
    }

    @Then("the list of todos has decreased by $amount todo")
    public void theListOfTodosHasDecreasdBy(int amount) throws Exception {
        dataChecker.verifyTableData("todo", DELETED_TODO_EXPECTED_DATA_SET);
    }

    @AfterScenario
    public void tearDownDatabase() throws Exception {
        databaseTester.onTearDown();
    }
}