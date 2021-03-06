/**
 * 
 */
package poc.jbehave.todo.test.step;

import static org.assertj.core.api.Assertions.assertThat;

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
import poc.jbehave.todo.service.api.UpdatedTodoDto;
import poc.jbehave.todo.service.domain.Todo;

/**
 * Définition des étapes JBehave.
 * 
 * @author Xavier Pigeon
 */
@StepsDefinition
public class TodoSteps {

    private static final String TODO_TABLE = "todo";
    private static final String ALL_TODOS_DATA_SET = "xml/dataset/todoDataSet.xml";
    private static final String NEW_TODO_EXPECTED_DATA_SET = "xml/dataset/newTodoExpectedDataSet.xml";
    private static final String DELETED_TODO_EXPECTED_DATA_SET = "xml/dataset/deletedTodoExpectedDataSet.xml";
    private static final String UPDATED_TODO_LABEL_EXPECTED_DATA_SET = "xml/dataset/updatedTodoLabelExpectedDataSet.xml";
    private static final String UPDATED_TODO_DONE_STATUS_EXPECTED_DATA_SET = "xml/dataset/updatedTodoDoneStatusExpectedDataSet.xml";

    @Autowired
    private ITodoService todoService;
    private AllTodosDto allTodosDto;
    private NewTodoDto newTodoDto;
    private UpdatedTodoDto updatedTodoDto;
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

    @When("I replace the todo $number by this label \"$label\"")
    public void iReplaceTodoByLabel(Long number, String label) {
        updatedTodoDto = todoService.updateLabel(number, label);
    }

    @When("I finish the todo $number")
    public void iFinishTodo(Long number) {
        updatedTodoDto = todoService.achieveTodo(number);
    }

    @Then("I get $number todos")
    public void iGetTodos(Integer number) throws Exception {
        assertThat(allTodosDto.getTodos()).hasSize(number);
        assertThat(allTodosDto.getTodos()).containsExactly( //
                new Todo(1L, "Ecrire les tests d'acceptation JBehave.", false), //
                new Todo(2L, "Constituer les jeux de données de test pour DbUnit.", false), //
                new Todo(3L, "Implémenter la couche de service.", false), //
                new Todo(4L, "Implémenter la couche de persistance.", false));

        dataChecker.verifyTableData(TODO_TABLE, ALL_TODOS_DATA_SET);
    }

    @Then("the last added todo is labeled \"$label\"")
    public void theLastAddedTodoIsLabeled(String label) {
        assertThat(newTodoDto.getNewTodo().getLabel()).isEqualTo(label);
    }

    @Then("the last added todo is not done")
    public void theLastAddedTodoIsNotDone() {
        assertThat(newTodoDto.getNewTodo().isDone()).isFalse();
    }

    @Then("the list of todos has grew by this last todo")
    public void theListOfTodosHasGrewByThisLastTodo() throws Exception {
        dataChecker.verifyTableData(TODO_TABLE, NEW_TODO_EXPECTED_DATA_SET);
    }

    @Then("the list of todos has decreased by $amount todo")
    public void theListOfTodosHasDecreasdBy(int amount) throws Exception {
        dataChecker.verifyTableData(TODO_TABLE, DELETED_TODO_EXPECTED_DATA_SET);
    }

    @Then("the todo $number label has been replaced by \"$label\"")
    public void theTodoLabelHasBeenUpdated(Long number, String label) throws Exception {
        Todo updatedTodo = updatedTodoDto.getUpdatedTodo();
        assertThat(updatedTodo.getId()).isEqualTo(number);
        assertThat(updatedTodo.getLabel()).isEqualTo(label);
        dataChecker.verifyTableData(TODO_TABLE, UPDATED_TODO_LABEL_EXPECTED_DATA_SET);
    }

    @Then("the todo $number is done")
    public void theTodoIsDone(Long number) throws Exception {
        Todo updatedTodo = updatedTodoDto.getUpdatedTodo();
        assertThat(updatedTodo.getId()).isEqualTo(number);
        assertThat(updatedTodo.isDone()).isTrue();
        dataChecker.verifyTableData(TODO_TABLE, UPDATED_TODO_DONE_STATUS_EXPECTED_DATA_SET);
    }

    @AfterScenario
    public void tearDownDatabase() throws Exception {
        databaseTester.onTearDown();
    }
}